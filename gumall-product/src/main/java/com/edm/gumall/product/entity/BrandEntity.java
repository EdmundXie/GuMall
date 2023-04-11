package com.edm.gumall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.edm.common.valid.AddGroup;
import com.edm.common.valid.ListValue;
import com.edm.common.valid.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 17:13:27
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	@Null(message = "Add brand must not contain Id",groups = {AddGroup.class})
	@NotNull(message = "Update brand must contain Id",groups = {UpdateGroup.class})
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "Brand name can't be empty", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotEmpty(message = "Logo can't be empty", groups = {AddGroup.class})
	@URL(message = "Url must be valid", groups = {AddGroup.class, UpdateGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@ListValue(vals={0,1},groups = {AddGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotEmpty(message = "FirstLetter can't be empty", groups = {AddGroup.class})
	@Pattern(regexp = "^[a-zA-Z]$",message = "First letter must be an English letter", groups = {AddGroup.class, UpdateGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(message = "Sort can't be null", groups = {AddGroup.class})
	@Min(value = 0,message = "Sort must be a positive integer", groups = {AddGroup.class, UpdateGroup.class})
	private Integer sort;

}
