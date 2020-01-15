package com.ccx.jsj.model.domain;

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
}
