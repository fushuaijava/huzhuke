package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_house_facility database table.
 * 
 */
@Entity
@Table(name="t_house_facility")
@NamedQuery(name="THouseFacility.findAll", query="SELECT t FROM THouseFacility t")
public class THouseFacility implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="air_hotcool")
	private int airHotcool;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String description;

	@Column(name="has_bath")
	private int hasBath;

	@Column(name="has_catv")
	private int hasCatv;

	@Column(name="has_network")
	private int hasNetwork;

	@Column(name="has_tv")
	private int hasTv;

	@Column(name="has_washer")
	private int hasWasher;

	@Column(name="has_wifi")
	private int hasWifi;

	@Column(name="house_id")
	private int houseId;

	private int isdelete;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="user_id")
	private int userId;

	public THouseFacility() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAirHotcool() {
		return this.airHotcool;
	}

	public void setAirHotcool(int airHotcool) {
		this.airHotcool = airHotcool;
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

	public int getHasBath() {
		return this.hasBath;
	}

	public void setHasBath(int hasBath) {
		this.hasBath = hasBath;
	}

	public int getHasCatv() {
		return this.hasCatv;
	}

	public void setHasCatv(int hasCatv) {
		this.hasCatv = hasCatv;
	}

	public int getHasNetwork() {
		return this.hasNetwork;
	}

	public void setHasNetwork(int hasNetwork) {
		this.hasNetwork = hasNetwork;
	}

	public int getHasTv() {
		return this.hasTv;
	}

	public void setHasTv(int hasTv) {
		this.hasTv = hasTv;
	}

	public int getHasWasher() {
		return this.hasWasher;
	}

	public void setHasWasher(int hasWasher) {
		this.hasWasher = hasWasher;
	}

	public int getHasWifi() {
		return this.hasWifi;
	}

	public void setHasWifi(int hasWifi) {
		this.hasWifi = hasWifi;
	}

	public int getHouseId() {
		return this.houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public int getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
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

}