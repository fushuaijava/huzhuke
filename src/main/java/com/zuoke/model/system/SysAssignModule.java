package com.zuoke.model.system;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sys_assign_module database table.
 * 
 */
@Entity
@Table(name="sys_assign_module")
@NamedQuery(name="SysAssignModule.findAll", query="SELECT s FROM SysAssignModule s")
public class SysAssignModule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="MODULE_ID")
	private int moduleId;

	@Column(name="ROLE_ID")
	private int roleId;

	public SysAssignModule() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}