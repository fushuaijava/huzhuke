package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_sign_history database table.
 * 
 */
@Entity
@Table(name="t_sign_history")
@NamedQuery(name="TSignHistory.findAll", query="SELECT t FROM TSignHistory t")
public class TSignHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="after_sign_level")
	private int afterSignLevel;

	@Column(name="before_sign_level")
	private int beforeSignLevel;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="user_id")
	private int userId;

	public TSignHistory() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAfterSignLevel() {
		return this.afterSignLevel;
	}

	public void setAfterSignLevel(int afterSignLevel) {
		this.afterSignLevel = afterSignLevel;
	}

	public int getBeforeSignLevel() {
		return this.beforeSignLevel;
	}

	public void setBeforeSignLevel(int beforeSignLevel) {
		this.beforeSignLevel = beforeSignLevel;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}