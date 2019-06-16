package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_user_product database table.
 * 
 */
@Entity
@Table(name="t_user_product")
@NamedQuery(name="TUserProduct.findAll", query="SELECT t FROM TUserProduct t")
public class TUserProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="user_id")
	private int userId;
	@Column(name="advise_coin")
	private double adviseCoin;

	private String bedcount;

	private String bedtype;

	@Column(name="compress_image1")
	private String compressImage1;

	@Column(name="compress_image2")
	private String compressImage2;

	@Column(name="compress_image3")
	private String compressImage3;

	@Column(name="compress_image4")
	private String compressImage4;

	@Column(name="compress_image5")
	private String compressImage5;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String desc;

	private String description;

	@Column(name="house_id")
	private int houseId;

	private String producttype;

	@Column(name="is_welcome")
	private String isWelcome;

	private int isdelete;

	@Column(name="min_coin")
	private double minCoin;

	private String name;

	private String picture1;

	private String picture2;

	private String picture3;

	private String picture4;

	private String picture5;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	@Transient
	private THouse tHouse;

	public TUserProduct() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAdviseCoin() {
		return this.adviseCoin;
	}

	public void setAdviseCoin(double adviseCoin) {
		this.adviseCoin = adviseCoin;
	}

	public String getBedcount() {
		return this.bedcount;
	}

	public void setBedcount(String bedcount) {
		this.bedcount = bedcount;
	}

	public String getBedtype() {
		return this.bedtype;
	}

	public void setBedtype(String bedtype) {
		this.bedtype = bedtype;
	}

	public String getCompressImage1() {
		return this.compressImage1;
	}

	public void setCompressImage1(String compressImage1) {
		this.compressImage1 = compressImage1;
	}

	public String getCompressImage2() {
		return this.compressImage2;
	}

	public void setCompressImage2(String compressImage2) {
		this.compressImage2 = compressImage2;
	}

	public String getCompressImage3() {
		return this.compressImage3;
	}

	public void setCompressImage3(String compressImage3) {
		this.compressImage3 = compressImage3;
	}

	public String getCompressImage4() {
		return this.compressImage4;
	}

	public void setCompressImage4(String compressImage4) {
		this.compressImage4 = compressImage4;
	}

	public String getCompressImage5() {
		return this.compressImage5;
	}

	public void setCompressImage5(String compressImage5) {
		this.compressImage5 = compressImage5;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHouseId() {
		return this.houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public String getIsWelcome() {
		return this.isWelcome;
	}

	public void setIsWelcome(String isWelcome) {
		this.isWelcome = isWelcome;
	}

	public int getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}

	public double getMinCoin() {
		return this.minCoin;
	}

	public void setMinCoin(double minCoin) {
		this.minCoin = minCoin;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture1() {
		return this.picture1;
	}

	public void setPicture1(String picture1) {
		this.picture1 = picture1;
	}

	public String getPicture2() {
		return this.picture2;
	}

	public void setPicture2(String picture2) {
		this.picture2 = picture2;
	}

	public String getPicture3() {
		return this.picture3;
	}

	public void setPicture3(String picture3) {
		this.picture3 = picture3;
	}

	public String getPicture4() {
		return this.picture4;
	}

	public void setPicture4(String picture4) {
		this.picture4 = picture4;
	}

	public String getPicture5() {
		return this.picture5;
	}

	public void setPicture5(String picture5) {
		this.picture5 = picture5;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public THouse gettHouse() {
		return tHouse;
	}

	public void settHouse(THouse tHouse) {
		this.tHouse = tHouse;
	}

}