package com.yf.pic.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cykj.grcloud.mybatis.GenericBase.BaseServiceImpl;
import com.yf.pic.entity.PluginPic;
import com.yf.pic.mapper.PluginPicMapper;

@Service
@Transactional
public class PluginPicServiceImpl extends BaseServiceImpl<PluginPic, Long>
		implements PluginPicService {
	@Autowired
	PluginPicMapper mapper;
	@Autowired
	public void setMapper(PluginPicMapper mapper) {
		setGenericMapper(mapper);
	}

	@Override
	public void deleteByCondition(Map<String, Object> condition) {
		mapper.deleteByCondition(condition);
	}
	
	@Override
	public List<PluginPic> getAllbyCondition(Map<String, Object> condition){
		return mapper.getAllbyCondition(condition);
	}
}
