package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_house_desc database table.
 * 
 */
@Entity
@Table(name="t_house_desc")
@NamedQuery(name="THouseDesc.findAll", query="SELECT t FROM THouseDesc t")
public class THouseDesc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="advise_coin")
	private double adviseCoin;

	private String arrive;

	private String comment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="house_id")
	private THouse tHouse;
	@Transient
	private Integer houseId;
	private int isdelete;

	@Column(name="min_coin")
	private double minCoin;

	@Column(name="once_receive")
	private String onceReceive;

	private String total;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="user_id")
	private int userId;

	private String vehicle;

	public THouseDesc() {
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

	public String getArrive() {
		return this.arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public THouse gettHouse() {
		return tHouse;
	}

	public void settHouse(THouse tHouse) {
		this.tHouse = tHouse;
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

	public String getOnceReceive() {
		return this.onceReceive;
	}

	public void setOnceReceive(String onceReceive) {
		this.onceReceive = onceReceive;
	}

	public String getTotal() {
		return this.total;
	}

	public void setTotal(String total) {
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

	public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

}