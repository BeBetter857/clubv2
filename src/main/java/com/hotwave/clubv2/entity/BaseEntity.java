package com.hotwave.clubv2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description 在每个表中都有这些字段 所以我们使用一个父类 然后让其他类来继承这个类
 * @date 2022-06-29 21:43:00
 */

@Data
public class BaseEntity {
    //定义主键策略：跟随数据库的主键自增(Mybatis-plus提供)
    @TableId(value = "id", type = IdType.AUTO)
    private String id; //主键

    private Date createTime;//创建时间

    private Date updateTime;//更新时间

    private String updateBy;//更新人

    private String createBy;//创建人id

}
