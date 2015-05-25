package com.yf.pic.entity;

import java.io.Serializable;
import java.util.Date;

import com.cykj.grcloud.mybatis.annotation.Column;
import com.cykj.grcloud.mybatis.annotation.Table;

@Table(tabName="T_P_PLUGIN",autoIncrement="pluginId",pkId="pluginId")
public class Plugin implements Serializable {

	// 自增id
	@Column
	private Integer pluginId;

	// 组件名称
	@Column
	private String pluginName;

	// 组件图片
	@Column
	private String pluginPic;

	// 备注
	@Column
	private String pluginRemark;

	@Column
	private Integer pluginOrder;

	// 状态
	@Column
	private Integer picStatus;

	// 新增时间
	@Column
	private Date createTime;

	// 修改时间
	@Column
	private Date updateTime;

	public Integer getPluginId() {
		return this.pluginId;
	}

	public void setPluginId(Integer pluginId) {
		this.pluginId = pluginId;
	}

	public String getPluginName() {
		return this.pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName == null ? null : pluginName.trim();
	}

	public String getPluginPic() {
		return this.pluginPic;
	}

	public void setPluginPic(String pluginPic) {
		this.pluginPic = pluginPic == null ? null : pluginPic.trim();
	}

	public String getPluginRemark() {
		return this.pluginRemark;
	}

	public void setPluginRemark(String pluginRemark) {
		this.pluginRemark = pluginRemark == null ? null : pluginRemark.trim();
	}

	public Integer getPluginOrder() {
		return this.pluginOrder;
	}

	public void setPluginOrder(Integer pluginOrder) {
		this.pluginOrder = pluginOrder;
	}

	public Integer getPicStatus() {
		return this.picStatus;
	}

	public void setPicStatus(Integer picStatus) {
		this.picStatus = picStatus;
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
