package com.ccx.jsj.model.domain;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.*;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity extends SuperEntity {

    private Date updateDate;

    private String updator;

    /**
     * mybatis-plus 乐观锁
     * 使用前先查看官方注意事项
     */
    @Version
    private Integer version;
}
