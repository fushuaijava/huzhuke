package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_userinfo database table.
 * 
 */
@Entity
@Table(name="t_userinfo")
@NamedQuery(name="TUserinfo.findAll", query="SELECT t FROM TUserinfo t")
public class TUserinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String address;

	@Column(name="card_img1")
	private String cardImg1;

	@Column(name="card_img2")
	private String cardImg2;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String email;

	private String father;

	private String idcard;

	private int isdelete;

	private String mobile;

	private String mother;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="user_id")
	private int userId;
	
	@Column(name="is_valid_info")
	private int isValidInfo;

	public TUserinfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCardImg1() {
		return this.cardImg1;
	}

	public void setCardImg1(String cardImg1) {
		this.cardImg1 = cardImg1;
	}

	public String getCardImg2() {
		return this.cardImg2;
	}

	public void setCardImg2(String cardImg2) {
		this.cardImg2 = cardImg2;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFather() {
		return this.father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public int getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMother() {
		return this.mother;
	}

	public void setMother(String mother) {
		this.mother = mother;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getIsValidInfo() {
		return isValidInfo;
	}

	public void setIsValidInfo(int isValidInfo) {
		this.isValidInfo = isValidInfo;
	}

}