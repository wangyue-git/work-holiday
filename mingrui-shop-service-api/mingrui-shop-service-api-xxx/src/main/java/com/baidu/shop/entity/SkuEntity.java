package com.baidu.shop.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ClassName SkuEntity
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/30
 * @Version V1.0
 **/
@Table(name = "tb_sku")
@Data
public class SkuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //新增返回主键
    private Long id;//新增id已经超过int的取值范围 需要用long类型

    private Integer spuId;

    private String title;

    private String images;

    private Integer price;

    private String indexes;

    private String ownSpec;

    private Integer enable;

    private Date createTime;

    private Date lastUpdateTime;
}
