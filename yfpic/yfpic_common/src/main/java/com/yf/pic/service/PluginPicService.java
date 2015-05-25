package com.yf.pic.service;

import java.util.List;
import java.util.Map;

import com.cykj.grcloud.mybatis.GenericBase.BaseService;
import com.cykj.grcloud.remote.service.RemoteService;
import com.yf.pic.entity.PluginPic;

@RemoteService
public interface PluginPicService extends BaseService<PluginPic, Long> {

	public void deleteByCondition(Map<String, Object> condition);
	
	public List<PluginPic> getAllbyCondition(Map<String, Object> condition);

}
