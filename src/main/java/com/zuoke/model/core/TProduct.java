package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_product database table.
 * 
 */
@Entity
@Table(name="t_product")
@NamedQuery(name="TProduct.findAll", query="SELECT t FROM TProduct t")
public class TProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="is_delete")
	private int isDelete;
	
	@Column(name="product_value")
	private int productValue;
	
	@Column(name="now_money")
	private double nowMoney;

	@Column(name="product_desc")
	private String productDesc;

	@Column(name="product_img")
	private String productImg;
	
	@Column(name="product_type")
	private String productType;
	
	@Column(name="product_money")
	private double productMoney;

	@Column(name="product_name")
	private String productName;

	@Column(name="product_small_img")
	private String productSmallImg;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public TProduct() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public double getNowMoney() {
		return this.nowMoney;
	}

	public void setNowMoney(double nowMoney) {
		this.nowMoney = nowMoney;
	}

	public String getProductDesc() {
		return this.productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductImg() {
		return this.productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public double getProductMoney() {
		return this.productMoney;
	}

	public void setProductMoney(double productMoney) {
		this.productMoney = productMoney;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductSmallImg() {
		return this.productSmallImg;
	}

	public void setProductSmallImg(String productSmallImg) {
		this.productSmallImg = productSmallImg;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public int getProductValue() {
		return productValue;
	}

	public void setProductValue(int productValue) {
		this.productValue = productValue;
	}

}