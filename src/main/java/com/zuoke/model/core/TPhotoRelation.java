package com.zuoke.model.core;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_photo_relation database table.
 * 
 */
@Entity
@Table(name="t_photo_relation")
@NamedQuery(name="TPhotoRelation.findAll", query="SELECT t FROM TPhotoRelation t")
public class TPhotoRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="file_id")
	private TPhotoFile tPhotoFile;
	
	@Column(name="group_type")
	private String groupType;

	@Column(name="is_delete")
	private int isDelete;

	@Column(name="relation_table_id")
	private int relationTableId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public TPhotoRelation() {
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

	public TPhotoFile gettPhotoFile() {
		return tPhotoFile;
	}

	public void settPhotoFile(TPhotoFile tPhotoFile) {
		this.tPhotoFile = tPhotoFile;
	}

	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public int getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getRelationTableId() {
		return this.relationTableId;
	}

	public void setRelationTableId(int relationTableId) {
		this.relationTableId = relationTableId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}