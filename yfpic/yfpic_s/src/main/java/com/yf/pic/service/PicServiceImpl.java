package com.yf.pic.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cykj.grcloud.mybatis.GenericBase.BaseServiceImpl;
import com.yf.pic.entity.Pic;
import com.yf.pic.mapper.PicMapper;

@Service
@Transactional
public class PicServiceImpl extends BaseServiceImpl<Pic, Long> implements
		PicService {
	@Autowired
	PicMapper mapper;
	@Autowired
	public void setMapper(PicMapper mapper) {
		setGenericMapper(mapper);
	}

	@Override
	public Integer removeByCondition(Map<String, Object> map) {
		return mapper.removeByCondition(map);
	}
}
