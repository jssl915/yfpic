package com.yf.pic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.cykj.grcloud.mybatis.GenericBase.GenericMapper;
import com.yf.pic.entity.UserPlugin;

public interface UserPluginMapper extends GenericMapper<UserPlugin,Long>{
	
	public Integer getCountByCondition(Map<String,Object> condititon);
	
	public List<UserPlugin> selectEntitysByCondition(Map<String,Object> condititon);
	
	public List<UserPlugin> selectEntitysByCondition(Map<String,Object> condititon, RowBounds rowBounds);
}
