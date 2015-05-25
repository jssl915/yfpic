package com.yf.pic.entity;

import java.io.Serializable;
import java.util.Date;

import com.cykj.grcloud.mybatis.annotation.Column;
import com.cykj.grcloud.mybatis.annotation.Table;

@Table(tabName="T_P_PIC",autoIncrement="picId",pkId="picId")
public class Pic implements Serializable{

	//自增id
	@Column
	private Integer picId; 

	@Column
	private Integer picUserId; 

	//图片url
	@Column
	private String picUrl; 

	//排序
	@Column
	private Integer picOrder; 

	//备注
	@Column
	private String picRemark; 

	//状态
	@Column
	private Integer picStatus; 

	//修改时间
	@Column
	private Date createTime; 
	
	@Column
	private Integer picType;
	
	@Column
	private String picName;

	public Integer  getPicId(){
		return this.picId;
	}

	public void setPicId(Integer picId){
		this.picId=picId;
	}

	public Integer  getPicUserId(){
		return this.picUserId;
	}

	public void setPicUserId(Integer picUserId){
		this.picUserId=picUserId;
	}

	public String  getPicUrl(){
		return this.picUrl;
	}

	public void setPicUrl(String picUrl){
		this.picUrl = picUrl == null ? null : picUrl.trim();
	}

	public Integer  getPicOrder(){
		return this.picOrder;
	}

	public void setPicOrder(Integer picOrder){
		this.picOrder=picOrder;
	}

	public String  getPicRemark(){
		return this.picRemark;
	}

	public void setPicRemark(String picRemark){
		this.picRemark = picRemark == null ? null : picRemark.trim();
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

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}
}
