package com.baidu.shop.dto;

import com.baidu.shop.base.BaseDTO;
import com.baidu.shop.validata.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName BrandDto
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/22
 * @Version V1.0
 **/
@Data
@ApiModel(value = "品牌DTO")
public class BrandDto extends BaseDTO {

    @ApiModelProperty(value = "品牌主键",example = "1")
    @NotNull(message = "主键不能为空",groups={MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value = "品牌名称")
    @NotNull(message = "名牌名称不能为空",groups={MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String name;

    @ApiModelProperty(value = "品牌图片")
    private String image;

    @ApiModelProperty(value = "品牌首字母")
    private Character letter;
}
