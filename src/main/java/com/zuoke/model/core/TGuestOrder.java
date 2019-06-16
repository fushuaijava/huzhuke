package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * The persistent class for the t_guest_order database table.
 * 
 */
@Entity
@Table(name="t_guest_order")
@NamedQuery(name="TGuestOrder.findAll", query="SELECT t FROM TGuestOrder t")
public class TGuestOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="after_pay")
	private double afterPay;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="g_comment_level")
	private int gCommentLevel;

	@Column(name="guest_user_id")
	private int guestUserId;
	
	@Transient
	private TUser guestUser;

	@Column(name="house_id")
	private int houseId;
	
	@Transient
	private THouse tHouse;

	@Column(name="is_delete")
	private int isDelete;

	@Column(name="m_comment_level")
	private int mCommentLevel;

	@Column(name="master_user_id")
	private int masterUserId;
	
	@Transient
	private TUser masterUser;

	@Column(name="order_state")
	private int orderState;

	@Column(name="pre_pay")
	private double prePay;
	
	@Column(name="add_pay")
	private double addPay;
	
	@Column(name="total_pay")
	private double totalPay;
	
	@Column(name="advise_coin")
	private double adviseCoin;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="guest_start_Time")
	private Date guestStartTime;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pre_pay_time")
	private Date prePayTime;
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="guest_end_Time")
	private Date guestEndTime;
	
	private Integer userproid;

	public Integer getUserproid() {
		return userproid;
	}

	public void setUserproid(Integer userproid) {
		this.userproid = userproid;
	}

	public TGuestOrder() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAfterPay() {
		return this.afterPay;
	}

	public void setAfterPay(double afterPay) {
		this.afterPay = afterPay;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getGCommentLevel() {
		return this.gCommentLevel;
	}

	public void setGCommentLevel(int gCommentLevel) {
		this.gCommentLevel = gCommentLevel;
	}

	public int getGuestUserId() {
		return this.guestUserId;
	}

	public void setGuestUserId(int guestUserId) {
		this.guestUserId = guestUserId;
	}

	public int getHouseId() {
		return this.houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public int getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getMCommentLevel() {
		return this.mCommentLevel;
	}

	public void setMCommentLevel(int mCommentLevel) {
		this.mCommentLevel = mCommentLevel;
	}

	public int getMasterUserId() {
		return this.masterUserId;
	}

	public void setMasterUserId(int masterUserId) {
		this.masterUserId = masterUserId;
	}

	public int getOrderState() {
		return this.orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}

	public double getPrePay() {
		return this.prePay;
	}

	public void setPrePay(double prePay) {
		this.prePay = prePay;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getgCommentLevel() {
		return gCommentLevel;
	}

	public void setgCommentLevel(int gCommentLevel) {
		this.gCommentLevel = gCommentLevel;
	}

	public TUser getGuestUser() {
		return guestUser;
	}

	public void setGuestUser(TUser guestUser) {
		this.guestUser = guestUser;
	}

	public THouse gettHouse() {
		return tHouse;
	}

	public void settHouse(THouse tHouse) {
		this.tHouse = tHouse;
	}

	public int getmCommentLevel() {
		return mCommentLevel;
	}

	public void setmCommentLevel(int mCommentLevel) {
		this.mCommentLevel = mCommentLevel;
	}

	public TUser getMasterUser() {
		return masterUser;
	}

	public void setMasterUser(TUser masterUser) {
		this.masterUser = masterUser;
	}

	public double getAddPay() {
		return addPay;
	}

	public void setAddPay(double addPay) {
		this.addPay = addPay;
	}

	public double getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(double totalPay) {
		this.totalPay = totalPay;
	}

	public double getAdviseCoin() {
		return adviseCoin;
	}

	public void setAdviseCoin(double adviseCoin) {
		this.adviseCoin = adviseCoin;
	}

	public Date getGuestStartTime() {
		return guestStartTime;
	}

	public void setGuestStartTime(Date guestStartTime) {
		this.guestStartTime = guestStartTime;
	}

	public Date getGuestEndTime() {
		return guestEndTime;
	}

	public void setGuestEndTime(Date guestEndTime) {
		this.guestEndTime = guestEndTime;
	}

	public Date getPrePayTime() {
		return prePayTime;
	}

	public void setPrePayTime(Date prePayTime) {
		this.prePayTime = prePayTime;
	}

}