package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_house database table.
 * 
 */
@Entity
@Table(name="t_house")
@NamedQuery(name="THouse.findAll", query="SELECT t FROM THouse t")
public class THouse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String address;

	@Column(name="advise_coin")
	private double adviseCoin;

	private String arrive;

	private String cate;

	private String city;

	@Column(name="comment_score")
	private int commentScore;
	//前台显示评价得分
	@Transient
	private double score;

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

	private String country;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String description;

	@Column(name="house_place")
	private String housePlace;

	private String houseicon;

	private String housetype;

	@Column(name="is_welcome")
	private String isWelcome;

	private int isdelete;

	@Column(name="min_coin")
	private double minCoin;

	private String nearby;

	@Column(name="once_receive")
	private String onceReceive;

	private String picture1;

	private String picture2;

	private String picture3;

	private String picture4;

	private String picture5;

	private String province;

	@Column(name="search_string")
	private String searchString;

	private String smalldes;

	private String special;
	//总访问数
	private int total;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="user_id")
	private int userId;
	
	@Transient
	private TUser tUser;
	@Transient
	private THouseFacility tHouseFacility;
	
	private String vehicle;
	
	private int canlive;
	
	private int indictment;
	@Column(name="room_count")
	private int roomCount;

	public THouse() {
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

	public double getAdviseCoin() {
		return this.adviseCoin;
	}

	public void setAdviseCoin(double adviseCoin) {
		this.adviseCoin = adviseCoin;
	}

	public String getArrive() {
		return this.arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	public String getCate() {
		return this.cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCommentScore() {
		return this.commentScore;
	}

	public void setCommentScore(int commentScore) {
		this.commentScore = commentScore;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHousePlace() {
		return this.housePlace;
	}

	public void setHousePlace(String housePlace) {
		this.housePlace = housePlace;
	}

	public String getHouseicon() {
		return this.houseicon;
	}

	public void setHouseicon(String houseicon) {
		this.houseicon = houseicon;
	}

	public String getHousetype() {
		return this.housetype;
	}

	public void setHousetype(String housetype) {
		this.housetype = housetype;
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

	public String getNearby() {
		return this.nearby;
	}

	public void setNearby(String nearby) {
		this.nearby = nearby;
	}

	public String getOnceReceive() {
		return this.onceReceive;
	}

	public void setOnceReceive(String onceReceive) {
		this.onceReceive = onceReceive;
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

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSearchString() {
		return this.searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSmalldes() {
		return this.smalldes;
	}

	public void setSmalldes(String smalldes) {
		this.smalldes = smalldes;
	}

	public String getSpecial() {
		return this.special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
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

	public String getVehicle() {
		return this.vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public TUser gettUser() {
		return tUser;
	}

	public void settUser(TUser tUser) {
		this.tUser = tUser;
	}

	public double getScore() {
		return  total==0?0:commentScore/total;
	}


	public int getCanlive() {
		return canlive;
	}

	public void setCanlive(int canlive) {
		this.canlive = canlive;
	}

	public int getIndictment() {
		return indictment;
	}

	public void setIndictment(int indictment) {
		this.indictment = indictment;
	}

	

	public THouseFacility gettHouseFacility() {
		return tHouseFacility;
	}

	public void settHouseFacility(THouseFacility tHouseFacility) {
		this.tHouseFacility = tHouseFacility;
	}

	public int getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(int roomCount) {
		this.roomCount = roomCount;
	}

	public void setScore(double score) {
		this.score = score;
	}

}