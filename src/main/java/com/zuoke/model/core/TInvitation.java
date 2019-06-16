package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_invitation database table.
 * 
 */
@Entity
@Table(name="t_invitation")
@NamedQuery(name="TInvitation.findAll", query="SELECT t FROM TInvitation t")
public class TInvitation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="big_image1")
	private String bigImage1;

	@Column(name="big_image2")
	private String bigImage2;

	@Column(name="big_image3")
	private String bigImage3;

	@Column(name="big_image4")
	private String bigImage4;

	@Column(name="compress_image1")
	private String compressImage1;

	@Column(name="compress_image2")
	private String compressImage2;

	@Column(name="compress_image3")
	private String compressImage3;

	@Column(name="compress_image4")
	private String compressImage4;

	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private int isdelete;

	@Column(name="praise_count")
	private int praiseCount;

	@Column(name="revice_count")
	private int reviceCount;

	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="user_id")
	private int userId;
	
	@Transient
	private TUser tUser;

	public TInvitation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBigImage1() {
		return this.bigImage1;
	}

	public void setBigImage1(String bigImage1) {
		this.bigImage1 = bigImage1;
	}

	public String getBigImage2() {
		return this.bigImage2;
	}

	public void setBigImage2(String bigImage2) {
		this.bigImage2 = bigImage2;
	}

	public String getBigImage3() {
		return this.bigImage3;
	}

	public void setBigImage3(String bigImage3) {
		this.bigImage3 = bigImage3;
	}

	public String getBigImage4() {
		return this.bigImage4;
	}

	public void setBigImage4(String bigImage4) {
		this.bigImage4 = bigImage4;
	}

	public String getCompressImage1() {
		return this.compressImage1;
	}

	public void setCompressImage1(String compressImage1) {
		this.compressImage1 = compressImage1;
	}

	public String getCompressImage2() {
		return this.compressImage2;
	}

	public void setCompressImage2(String compressImage2) {
		this.compressImage2 = compressImage2;
	}

	public String getCompressImage3() {
		return this.compressImage3;
	}

	public void setCompressImage3(String compressImage3) {
		this.compressImage3 = compressImage3;
	}

	public String getCompressImage4() {
		return this.compressImage4;
	}

	public void setCompressImage4(String compressImage4) {
		this.compressImage4 = compressImage4;
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

	public int getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}

	public int getPraiseCount() {
		return this.praiseCount;
	}

	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}

	public int getReviceCount() {
		return this.reviceCount;
	}

	public void setReviceCount(int reviceCount) {
		this.reviceCount = reviceCount;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

}