package com.yf.pic.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cykj.grcloud.mybatis.GenericBase.BaseServiceImpl;
import com.yf.pic.entity.PicUser;
import com.yf.pic.mapper.PicUserMapper;
import com.yf.util.MD5Encoder;

@Service
@Transactional
public class PicUserServiceImpl extends BaseServiceImpl<PicUser, Long>
		implements PicUserService {

	@Autowired
	public void setMapper(PicUserMapper mapper) {
		setGenericMapper(mapper);
	}

	@Override
	public void insert(PicUser record) {
		record.setPStatus(1);
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		record.setPUserPsd(MD5Encoder.encode(record.getPUserPsd()));
		super.insert(record);
	}
	
	@Override
	public void updatePUserPwd(PicUser record) {
		record.setPUserPsd(MD5Encoder.encode(record.getPUserPsd()));
		record.setUpdateTime(new Date());
		super.mergeById(record);
	}
}
