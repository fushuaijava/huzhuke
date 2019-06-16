package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the t_help_info database table.
 * 
 */
@Entity
@Table(name="t_help_info")
@NamedQuery(name="THelpInfo.findAll", query="SELECT t FROM THelpInfo t")
public class THelpInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private int coin;
	@DateTimeFormat
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="help_info")
	private String helpInfo;

	private int isdelete;

	private String mobile;

	private String place;
	@DateTimeFormat
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="help_user_id")
	private int helpUserId;
	
	@Column(name="help_reply_id")
	private int helpReplyId;
	
	@Transient
	private TUser tUser;
	@Transient
	private TUser helpUser;
	
	@Column(name="praise_count")
	private int praiseCount;

	@Column(name="revice_count")
	private int reviceCount;
	//帮助状态：0创建，1有人回应，2接受求助，3对方帮助完成，5确认帮助完成，6支付报酬完成，10订单报结，11取消帮助
	@Column(name="state")
	private int state;
	
	@Transient
	private Page<THelpInfoReply> tHelpInfoReplys;
	@Transient
	private List<THelpInfoReply> tHelpInfoReplyList;
	
	public List<THelpInfoReply> gettHelpInfoReplyList() {
		return tHelpInfoReplys!=null?tHelpInfoReplys.getContent():null;
	}

	public void settHelpInfoReplyList(List<THelpInfoReply> tHelpInfoReplyList) {
		this.tHelpInfoReplyList = tHelpInfoReplyList;
	}

	public THelpInfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCoin() {
		return this.coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHelpInfo() {
		return this.helpInfo;
	}

	public void setHelpInfo(String helpInfo) {
		this.helpInfo = helpInfo;
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

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public TUser gettUser() {
		return tUser;
	}

	public void settUser(TUser tUser) {
		this.tUser = tUser;
	}

	public int getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}

	public int getReviceCount() {
		return reviceCount;
	}

	public void setReviceCount(int reviceCount) {
		this.reviceCount = reviceCount;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getHelpUserId() {
		return helpUserId;
	}

	public void setHelpUserId(int helpUserId) {
		this.helpUserId = helpUserId;
	}

	public TUser getHelpUser() {
		return helpUser;
	}

	public void setHelpUser(TUser helpUser) {
		this.helpUser = helpUser;
	}

	public Page<THelpInfoReply> gettHelpInfoReplys() {
		return tHelpInfoReplys;
	}

	public void settHelpInfoReplys(Page<THelpInfoReply> tHelpInfoReplys) {
		this.tHelpInfoReplys = tHelpInfoReplys;
	}

	public int getHelpReplyId() {
		return helpReplyId;
	}

	public void setHelpReplyId(int helpReplyId) {
		this.helpReplyId = helpReplyId;
	}


}