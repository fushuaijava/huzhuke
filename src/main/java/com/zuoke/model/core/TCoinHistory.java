package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_coin_history database table.
 * 
 */
@Entity
@Table(name="t_coin_history")
@NamedQuery(name="TCoinHistory.findAll", query="SELECT t FROM TCoinHistory t")
public class TCoinHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	//渠道 sign签到 setinfo完善资料 send赠送  recharge充值 cost消费 trade 交易 conversion兑换
	private String channel;

	private int count;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;
	@Column(name="desc_info")
	private String descInfo;

	private int isadd;

	private double newcoin;

	private double oldcoin;
	
	@Column(name="new_lock_coin")
	private double newLockCoin;
	
	@Column(name="old_lock_coin")
	private double oldLockCoin;
	
	@Column(name="is_system")
	private String  isSystem;

	@Column(name="trade_type")
	private String tradeType;

	@Column(name="trade_user_id")
	private int tradeUserId;

	private String tradeuser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="user_id")
	private int userId;

	public TCoinHistory() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getDescInfo() {
		return descInfo;
	}

	public void setDescInfo(String descInfo) {
		this.descInfo = descInfo;
	}

	public int getIsadd() {
		return this.isadd;
	}

	public void setIsadd(int isadd) {
		this.isadd = isadd;
	}

	public double getNewcoin() {
		return this.newcoin;
	}

	public void setNewcoin(double newcoin) {
		this.newcoin = newcoin;
	}

	public double getOldcoin() {
		return this.oldcoin;
	}

	public void setOldcoin(double oldcoin) {
		this.oldcoin = oldcoin;
	}

	

	public String getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}

	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public int getTradeUserId() {
		return this.tradeUserId;
	}

	public void setTradeUserId(int tradeUserId) {
		this.tradeUserId = tradeUserId;
	}

	public String getTradeuser() {
		return this.tradeuser;
	}

	public void setTradeuser(String tradeuser) {
		this.tradeuser = tradeuser;
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

	public double getNewLockCoin() {
		return newLockCoin;
	}

	public void setNewLockCoin(double newLockCoin) {
		this.newLockCoin = newLockCoin;
	}

	public double getOldLockCoin() {
		return oldLockCoin;
	}

	public void setOldLockCoin(double oldLockCoin) {
		this.oldLockCoin = oldLockCoin;
	}

}