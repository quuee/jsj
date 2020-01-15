package com.ccx.jsj.commons.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


/**
 * mybaits-plus wrapper对象的封装
 * 简化后台管理时的一些入参过滤查询
 * 切记 只能用作单表过滤查询
 *
 *
 * @see AbsWrapper#page 默认当前分页 = 第0页
 * @see AbsWrapper#pageSize 默认每页条数 = 20条
 *
 * @see AbsWrapper#setWrap()  子类需实现的抽象条件
 *
 * @see AbsWrapper#getList() 根据setWrap的条件 获取所有符合的数据
 * @see AbsWrapper#getIPage() 根据setWrap的条件 获取符合的分页数据
 */
public abstract class AbsWrapper<T> implements WrapperOperation<T>{

    protected int page = 0;

    protected int pageSize = 20;

    protected QueryWrapper<T> wrapper = new QueryWrapper<>();

    //这里没有注入BaseMapper 空指针
//    protected BaseMapper<T> baseMapper;

//    public List<T> getList(){
//        setWrap();
//        return baseMapper.selectList(wrapper);
//    }

//    public IPage<T> getPageList(){
//        setWrap();
//        return baseMapper.selectPage(getIPage(), wrapper);
//    }

    public Page<T> getIPage(){
        return new Page<>(page,pageSize);
    }

    public abstract void setWrap();

    @Override
    public QueryWrapper<T> getWrapper() {
        return wrapper;
    }
}
