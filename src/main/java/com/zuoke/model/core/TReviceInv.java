package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_revice_inv database table.
 * 
 */
@Entity
@Table(name="t_revice_inv")
@NamedQuery(name="TReviceInv.findAll", query="SELECT t FROM TReviceInv t")
public class TReviceInv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="invitation_id")
	private int invitationId;

	@Column(name="is_delete")
	private int isDelete;

	@Column(name="parent_id")
	private int parentId;

	@Column(name="reply_id")
	private int replyId;

	@Column(name="send_user_id")
	private int sendUserId;

	@Column(name="to_user_id")
	private int toUserId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="revice_count")
	private int reviceCount;
	
	@Transient
	private TUser sendUser;
	
	@Transient
	private TUser toUser;
	

	public TReviceInv() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getInvitationId() {
		return this.invitationId;
	}

	public void setInvitationId(int invitationId) {
		this.invitationId = invitationId;
	}

	public int getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
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

	public int getSendUserId() {
		return this.sendUserId;
	}

	public void setSendUserId(int sendUserId) {
		this.sendUserId = sendUserId;
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

	public TUser getSendUser() {
		return sendUser;
	}

	public void setSendUser(TUser sendUser) {
		this.sendUser = sendUser;
	}

	public TUser getToUser() {
		return toUser;
	}

	public void setToUser(TUser toUser) {
		this.toUser = toUser;
	}

	public int getReviceCount() {
		return reviceCount;
	}

	public void setReviceCount(int reviceCount) {
		this.reviceCount = reviceCount;
	}

}