package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_user database table.
 * 
 */
@Entity
@Table(name="t_user")
@NamedQuery(name="TUser.findAll", query="SELECT t FROM TUser t")
public class TUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="accept_count")
	private int acceptCount;

	private String city;

	private int coin;
	@Column(name="lock_coin")
	private int lockCoin;

	private String country;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private int gender;

	private String headimg;

	private int indictment;

	private int isdelete;
	//用户等级经验值(用来累计用户经验)
	private int level;
	//用户等级
	@Transient
	private int userLevel;

	private String nickname;

	private String password;

	private String province;
	
	private String language;

	private double score;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	private String userid;
	
	private String mobilephone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="vip_end_time")
	private Date vipEndTime;

	@Column(name="visit_count")
	private int visitCount;
	@Column(name="is_valid_info")
	private int isValidInfo;
	@Column(name="pay_pwd")
	private String payPwd;
	
	@Column(name="has_im_user")
	private int hasImUser;
	private String openid;
	
	@Transient
	private boolean isvip;
	
	@Transient
	private String impwd;
	
	
	public TUser() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAcceptCount() {
		return this.acceptCount;
	}

	public void setAcceptCount(int acceptCount) {
		this.acceptCount = acceptCount;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCoin() {
		return this.coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getLockCoin() {
		return lockCoin;
	}

	public void setLockCoin(int lockCoin) {
		this.lockCoin = lockCoin;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getGender() {
		return this.gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getHeadimg() {
		return this.headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public int getIndictment() {
		return this.indictment;
	}

	public void setIndictment(int indictment) {
		this.indictment = indictment;
	}

	public int getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}

	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public int getUserLevel() {
		int tempUserlevel=0;
		int tempLevel=level;
		do {
			tempUserlevel++;
			tempLevel=tempLevel/10;
		} while (tempLevel>0);
		return tempUserlevel;
	}

	public void setUserLevel(int userLevel) {
		
		this.userLevel = userLevel;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getVipEndTime() {
		return this.vipEndTime;
	}

	public void setVipEndTime(Date vipEndTime) {
		this.vipEndTime = vipEndTime;
	}

	public int getVisitCount() {
		return this.visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public int getIsValidInfo() {
		return isValidInfo;
	}

	public void setIsValidInfo(int isValidInfo) {
		this.isValidInfo = isValidInfo;
	}

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public boolean isIsvip() {
		return vipEndTime==null?false:new Date().before(vipEndTime);
	}

	public void setIsvip(boolean isvip) {
		this.isvip = isvip;
	}

	public int getHasImUser() {
		return hasImUser;
	}

	public void setHasImUser(int hasImUser) {
		this.hasImUser = hasImUser;
	}

	public String getImpwd() {
		return impwd;
	}

	public void setImpwd(String impwd) {
		this.impwd = impwd;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}