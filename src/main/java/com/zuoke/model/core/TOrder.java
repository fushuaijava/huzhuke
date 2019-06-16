package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_order database table.
 * 
 */
@Entity
@Table(name="t_order")
@NamedQuery(name="TOrder.findAll", query="SELECT t FROM TOrder t")
public class TOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="is_delete")
	private int isDelete;

	@Column(name="order_money")
	private double orderMoney;

	@Column(name="order_state")
	private int orderState;

	@Column(name="order_type")
	private int orderType;

	@Column(name="pay_money")
	private double payMoney;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pay_time")
	private Date payTime;

	@Column(name="product_id")
	private int productId;
	
	@Transient
	private TProduct tProduct;

	@Column(name="order_no")
	private String orderNo;
	
	@Column(name="product_name")
	private String productName;

	@Column(name="send_coin")
	private int sendCoin;

	@Column(name="send_vip")
	private int sendVip;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="user_id")
	private int userId;
	
	@Column(name="cost_coin")
	private int costCoin;
	@Transient
	private TUser tUser;

	public TOrder() {
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

	public double getOrderMoney() {
		return this.orderMoney;
	}

	public void setOrderMoney(double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public int getOrderState() {
		return this.orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}

	public int getOrderType() {
		return this.orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public double getPayMoney() {
		return this.payMoney;
	}

	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getSendCoin() {
		return this.sendCoin;
	}

	public void setSendCoin(int sendCoin) {
		this.sendCoin = sendCoin;
	}

	public int getSendVip() {
		return this.sendVip;
	}

	public void setSendVip(int sendVip) {
		this.sendVip = sendVip;
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

	public TProduct gettProduct() {
		return tProduct;
	}

	public void settProduct(TProduct tProduct) {
		this.tProduct = tProduct;
	}

	public TUser gettUser() {
		return tUser;
	}

	public void settUser(TUser tUser) {
		this.tUser = tUser;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getCostCoin() {
		return costCoin;
	}

	public void setCostCoin(int costCoin) {
		this.costCoin = costCoin;
	}

}