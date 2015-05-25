package com.yf.pic.mapper;

import java.util.List;
import java.util.Map;

import com.cykj.grcloud.mybatis.GenericBase.GenericMapper;
import com.yf.pic.entity.PluginPic;

public interface PluginPicMapper extends GenericMapper<PluginPic,Long>{
	
	public void deleteByCondition(Map<String, Object> condition);
	
	public List<PluginPic> getAllbyCondition(Map<String, Object> condition);

}
