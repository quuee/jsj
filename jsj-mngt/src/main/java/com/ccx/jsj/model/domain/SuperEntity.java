package com.ccx.jsj.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SuperEntity implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    private Date createDate;

    private Date creator;

    /**
     * 保存和缺省验证组
     */
    public interface Save extends Default {

    }

    /**
     * 更新和缺省验证组
     */
    public interface Update extends Default {

    }

}
