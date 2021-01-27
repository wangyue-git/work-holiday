package com.baidu.shop.dto;

import com.baidu.shop.base.BaseDTO;
import com.baidu.shop.validata.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

/**
 * @ClassName SpecParamDto
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/27
 * @Version V1.0
 **/

@Data
@ApiModel(value = "规格参数")
public class SpecParamDto extends BaseDTO {

    @ApiModelProperty(value = "主键",example = "1")
    @NotNull(message = "主键不能为空",groups = {MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value = "分类id",example = "1")
    @NotNull(message = "分类id不能为空",groups = {MingruiOperation.Update.class})
    private Integer cid;

    @ApiModelProperty(value = "规格组id",example = "1")
    @NotNull(message = "规格组id不能为空",groups = {MingruiOperation.Update.class})
    private Integer groupId;

    @ApiModelProperty(value = "规格组参数名称",example = "1")
    @NotEmpty(message = "规格组参数名称不能为空",groups = {MingruiOperation.Add.class})
    private String name;

    @ApiModelProperty(value = "是否是数字类型参数，1->true或0->false",example = "0")
    @NotNull(message = "是否是数字类型参数不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Boolean numeric;

    @ApiModelProperty(value = "数字类型参数的单位，非数字类型可以为空",example = "1")
    @NotEmpty(message = "数字类型参数的单位不能为空",groups = {MingruiOperation.Add.class})
    private String unit;

    @ApiModelProperty(value = "是否是sku通用属性，1->true或0->false",example = "0")
    @NotNull(message = "是否是sku通用属性不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Boolean generic;

    @ApiModelProperty(value = "是否用于搜索过滤，true或false",example = "0")
    @NotNull(message = "是否用于搜索过滤不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Boolean searching;

    @ApiModelProperty(value = "数值类型参数",example = "1")
    @NotEmpty(message = "数值类型参数不能为空",groups = {MingruiOperation.Add.class})
    private String segments;
}
