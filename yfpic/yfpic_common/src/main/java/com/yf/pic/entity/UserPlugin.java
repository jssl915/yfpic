package com.yf.pic.entity;

import java.io.Serializable;
import java.util.Date;

import com.cykj.grcloud.mybatis.annotation.Column;
import com.cykj.grcloud.mybatis.annotation.Table;

@Table(tabName="T_P_USER_PLUGIN",autoIncrement="userPluginId",pkId="userPluginId")
public class UserPlugin implements Serializable{

	//自增id
	@Column
	private Integer userPluginId; 

	//用户id
	@Column
	private Integer pUserId; 

	//组件id
	@Column
	private Integer pluginId; 

	//排序
	@Column
	private Integer pluginOrder; 

	@Column
	private Integer picStatus; 

	//新增时间
	@Column
	private Date createTime; 
	
	private String pluginName;

	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	public Integer  getUserPluginId(){
		return this.userPluginId;
	}

	public void setUserPluginId(Integer userPluginId){
		this.userPluginId=userPluginId;
	}

	public Integer  getPUserId(){
		return this.pUserId;
	}

	public void setPUserId(Integer pUserId){
		this.pUserId=pUserId;
	}

	public Integer  getPluginId(){
		return this.pluginId;
	}

	public void setPluginId(Integer pluginId){
		this.pluginId=pluginId;
	}

	public Integer  getPluginOrder(){
		return this.pluginOrder;
	}

	public void setPluginOrder(Integer pluginOrder){
		this.pluginOrder=pluginOrder;
	}

	public Integer  getPicStatus(){
		return this.picStatus;
	}

	public void setPicStatus(Integer picStatus){
		this.picStatus=picStatus;
	}

	public Date  getCreateTime(){
		return this.createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
}
