package com.yf.pic.entity;

import java.io.Serializable;
import java.util.Date;

import com.cykj.grcloud.mybatis.annotation.Column;
import com.cykj.grcloud.mybatis.annotation.Table;

@Table(tabName="T_P_USER",autoIncrement="pUserId",pkId="pUserId")
public class PicUser implements Serializable {

	// 自增id
	@Column
	private Integer pUserId;

	// 用户登录名
	@Column
	private String pUserName;

	// 真实姓名
	@Column
	private String pRealName;

	// 登录密码
	@Column
	private String pUserPsd;

	// 性别
	@Column
	private Integer pUserSex;

	// 手机号码
	@Column
	private String pMobile;

	// 邮箱
	@Column
	private String pUserEmail;

	// 排序
	@Column
	private Integer pUserOrder;

	// 备注
	@Column
	private String pUserRemark;

	// 状态
	@Column
	private Integer pStatus;

	// 新增时间
	@Column
	private Date createTime;

	// 修改时间
	@Column
	private Date updateTime;

	public Integer getPUserId() {
		return this.pUserId;
	}

	public void setPUserId(Integer pUserId) {
		this.pUserId = pUserId;
	}

	public String getPUserName() {
		return this.pUserName;
	}

	public void setPUserName(String pUserName) {
		this.pUserName = pUserName == null ? null : pUserName.trim();
	}

	public String getPRealName() {
		return this.pRealName;
	}

	public void setPRealName(String pRealName) {
		this.pRealName = pRealName == null ? null : pRealName.trim();
	}

	public String getPUserPsd() {
		return this.pUserPsd;
	}

	public void setPUserPsd(String pUserPsd) {
		this.pUserPsd = pUserPsd == null ? null : pUserPsd.trim();
	}

	public Integer getPUserSex() {
		return this.pUserSex;
	}

	public void setPUserSex(Integer pUserSex) {
		this.pUserSex = pUserSex;
	}

	public String getPMobile() {
		return this.pMobile;
	}

	public void setPMobile(String pMobile) {
		this.pMobile = pMobile == null ? null : pMobile.trim();
	}

	public String getPUserEmail() {
		return this.pUserEmail;
	}

	public void setPUserEmail(String pUserEmail) {
		this.pUserEmail = pUserEmail == null ? null : pUserEmail.trim();
	}

	public Integer getPUserOrder() {
		return this.pUserOrder;
	}

	public void setPUserOrder(Integer pUserOrder) {
		this.pUserOrder = pUserOrder;
	}

	public String getPUserRemark() {
		return this.pUserRemark;
	}

	public void setPUserRemark(String pUserRemark) {
		this.pUserRemark = pUserRemark == null ? null : pUserRemark.trim();
	}

	public Integer getPStatus() {
		return this.pStatus;
	}

	public void setPStatus(Integer pStatus) {
		this.pStatus = pStatus;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
