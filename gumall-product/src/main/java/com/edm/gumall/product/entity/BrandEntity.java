package com.edm.gumall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
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
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "brand name can't be empty")
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotEmpty
	@URL(message = "must be a valid url")
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z]$",message = "first letter must be an English letter")
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull
	@Min(value = 0,message = "sort must be a positive integer")
	private Integer sort;

}
