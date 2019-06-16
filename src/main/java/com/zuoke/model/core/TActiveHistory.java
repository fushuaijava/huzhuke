package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_active_history database table.
 * 
 */
@Entity
@Table(name="t_active_history")
@NamedQuery(name="TActiveHistory.findAll", query="SELECT t FROM TActiveHistory t")
public class TActiveHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="active_id")
	private int activeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="insert_time")
	private Date insertTime;

	@Column(name="is_delete")
	private Integer isDelete;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="user_id")
	private int userId;
	
	@Transient
	private TUser tUser;
	
	@Transient
	private TActive tActive;

	public TActiveHistory() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActiveId() {
		return this.activeId;
	}

	public void setActiveId(int activeId) {
		this.activeId = activeId;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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

	public TUser gettUser() {
		return tUser;
	}

	public void settUser(TUser tUser) {
		this.tUser = tUser;
	}

	public TActive gettActive() {
		return tActive;
	}

	public void settActive(TActive tActive) {
		this.tActive = tActive;
	}

}