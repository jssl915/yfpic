package com.yf.pic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cykj.grcloud.mybatis.GenericBase.BaseServiceImpl;
import com.yf.pic.entity.Plugin;
import com.yf.pic.mapper.PluginMapper;

@Service
@Transactional
public class PluginServiceImpl extends BaseServiceImpl<Plugin, Long> implements
		PluginService {

	@Autowired
	public void setMapper(PluginMapper mapper) {
		setGenericMapper(mapper);
	}
}
