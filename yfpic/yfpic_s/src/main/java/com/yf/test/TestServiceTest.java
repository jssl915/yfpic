package com.yf.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yf.system.mapper.SysUserMapper;
import com.yf.test.util.BaseTest;

public class TestServiceTest extends BaseTest{

	 @Autowired 
	 private SysUserMapper mapper;        
	 
	 @Test
	 public void mapperTest() {
		 mapper.countByCondition(null);
	 }
}
