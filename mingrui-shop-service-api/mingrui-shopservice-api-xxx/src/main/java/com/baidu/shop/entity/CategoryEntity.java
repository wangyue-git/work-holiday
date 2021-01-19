package com.baidu.shop.entity;

import com.baidu.shop.validata.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @ClassName CategoryEntity
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/19
 * @Version V1.0
 **/
@ApiModel(value = "分类实体类")
@Data
@Table(name="tb_category")
public class CategoryEntity {

    @Id//声明主键
    @ApiModelProperty(value = "分类主键",example = "1")//注解用于方法、字段，表示对model属性的说明或者数据操作更改
    @NotNull(message = "id不能为空",groups={MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value = "分类名称")
    @NotEmpty(message = "分类名称不能为空",groups={MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String name;

    @ApiModelProperty(value = "父级id",example = "1")
    @NotNull(message = "父级id不能为空",groups={MingruiOperation.Update.class})
    private Integer parentId;

    @ApiModelProperty(value = "是否为父级id,0否1是",example = "1")
    @NotNull(message = "是否为父级id不能为空",groups={MingruiOperation.Update.class})
    private Integer isParent;

    @ApiModelProperty(value = "排序指数,越小越靠前",example = "1")
    @NotNull(message = "排序指数不能为空",groups={MingruiOperation.Update.class})
    private Integer sort;
}
