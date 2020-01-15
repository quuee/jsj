package com.ccx.jsj.commons.util;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.experimental.UtilityClass;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.Collection;

/**
 * 批量操作工具
 */
@UtilityClass
public class BatchUtils {

    private  static SqlSessionTemplate sqlSessionTemplate = SpringApplicationUtils.getBean(SqlSessionTemplate.class);

    public<T> void batchInsert(Collection<T> collection){
        batchInsert(collection,100);
    }

    public<T>  void batchInsert(Collection<T> collection, int size)  {
        if (CollectionUtils.isEmpty(collection)) {
            return;
        }

        Class<?> aClass = collection.iterator().next().getClass();
        try (SqlSession sqlSession = getSqlSession()) {
            int i = 0;
            String sqlStatement = getSqlStatement(aClass, SqlMethod.INSERT_ONE);
            for (T anEntityList : collection) {
                sqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % size == 0) {
                    sqlSession.flushStatements();
                }
                i++;
            }
            sqlSession.flushStatements();
        } catch (Throwable e) {
            throw ExceptionUtils.mpe("Error: Cannot execute saveBatch Method. Cause", e);
        }
    }

    public <T> void batchUpdate(Collection<T> collection,int size){
        if (CollectionUtils.isEmpty(collection)) {
            return;
        }
        Class<?> aClass = collection.iterator().next().getClass();
        try (SqlSession sqlSession = getSqlSession()) {
            int i = 0;
            String sqlStatement = getSqlStatement(aClass,SqlMethod.UPDATE_BY_ID);
            for (T anEntityList : collection) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, anEntityList);
                sqlSession.update(sqlStatement, param);
                if (i >= 1 && i % size == 0) {
                    sqlSession.flushStatements();
                }
                i++;
            }
            sqlSession.flushStatements();
        } catch (Throwable e) {
            throw ExceptionUtils.mpe("Error: Cannot execute saveBatch Method. Cause", e);
        }
    }

    private SqlSession getSqlSession(){
        return sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH);
    }

    private String getSqlStatement(Class cls,SqlMethod sqlMethod){
        return SqlHelper.table(cls).getSqlStatement(sqlMethod.getMethod());
    }
}
