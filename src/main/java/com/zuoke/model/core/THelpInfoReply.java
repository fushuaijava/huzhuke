package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_help_info_reply database table.
 * 
 */
@Entity
@Table(name="t_help_info_reply")
@NamedQuery(name="THelpInfoReply.findAll", query="SELECT t FROM THelpInfoReply t")
public class THelpInfoReply implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="help_info_id")
	private int helpInfoId;

	private int isdelete;

	@Column(name="parent_id")
	private int parentId;

	@Column(name="reply_id")
	private int replyId;

	@Column(name="reply_info")
	private String replyInfo;

	@Column(name="to_user_id")
	private int toUserId;
	@Transient
	private TUser toUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="user_id")
	private int userId;
	@Transient
	private TUser tUser;
	
	@Transient
	private THelpInfo tHelpInfo; 

	public THelpInfoReply() {
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

	public int getHelpInfoId() {
		return this.helpInfoId;
	}

	public void setHelpInfoId(int helpInfoId) {
		this.helpInfoId = helpInfoId;
	}

	public int getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}

	public int getParentId() {
		return this.parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getReplyId() {
		return this.replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public String getReplyInfo() {
		return this.replyInfo;
	}

	public void setReplyInfo(String replyInfo) {
		this.replyInfo = replyInfo;
	}

	public int getToUserId() {
		return this.toUserId;
	}

	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
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

	public THelpInfo gettHelpInfo() {
		return tHelpInfo;
	}

	public void settHelpInfo(THelpInfo tHelpInfo) {
		this.tHelpInfo = tHelpInfo;
	}

	public TUser getToUser() {
		return toUser;
	}

	public void setToUser(TUser toUser) {
		this.toUser = toUser;
	}

	public TUser gettUser() {
		return tUser;
	}

	public void settUser(TUser tUser) {
		this.tUser = tUser;
	}

	

}