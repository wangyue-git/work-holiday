package com.baidu.shop.entity;

import com.sun.org.apache.xpath.internal.objects.XString;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName SpecGroupEntity
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/25
 * @Version V1.0
 **/
@Table(name = "tb_spec_group")
@Data
public class SpecGroupEntity {

    @Id
    private Integer id;

    private Integer cid;

    private String name;
}
