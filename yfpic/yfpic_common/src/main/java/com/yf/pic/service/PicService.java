package com.yf.pic.service;

import java.util.Map;

import com.cykj.grcloud.mybatis.GenericBase.BaseService;
import com.cykj.grcloud.remote.service.RemoteService;
import com.yf.pic.entity.Pic;

@RemoteService
public interface PicService extends BaseService<Pic,Long>{
	
	public Integer removeByCondition(Map<String,Object>map);

}
