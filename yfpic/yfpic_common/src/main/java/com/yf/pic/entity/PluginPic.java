package com.yf.pic.entity;

import java.io.Serializable;
import java.util.Date;

import com.cykj.grcloud.mybatis.annotation.Column;
import com.cykj.grcloud.mybatis.annotation.Table;

@Table(tabName="T_P_PLUGIN_PIC",autoIncrement="pluginPicId",pkId="pluginPicId")
public class PluginPic implements Serializable{

	//自增id
	@Column
	private Integer pluginPicId; 

	//图片id
	@Column
	private Integer picId; 

	//用户组件id
	@Column
	private Integer userPluginId; 

	//排序
	@Column
	private Integer pluginOrder; 

	//新增时间
	@Column
	private Date createTime; 
	
	private String picUrl;

	public Integer  getPluginPicId(){
		return this.pluginPicId;
	}

	public void setPluginPicId(Integer pluginPicId){
		this.pluginPicId=pluginPicId;
	}

	public Integer  getPicId(){
		return this.picId;
	}

	public void setPicId(Integer picId){
		this.picId=picId;
	}

	public Integer  getUserPluginId(){
		return this.userPluginId;
	}

	public void setUserPluginId(Integer userPluginId){
		this.userPluginId=userPluginId;
	}

	public Integer  getPluginOrder(){
		return this.pluginOrder;
	}

	public void setPluginOrder(Integer pluginOrder){
		this.pluginOrder=pluginOrder;
	}

	public Date  getCreateTime(){
		return this.createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
