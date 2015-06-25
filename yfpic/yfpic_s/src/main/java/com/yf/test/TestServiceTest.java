package com.yf.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yf.pic.entity.Plugin;
import com.yf.pic.mapper.UserPluginMapper;
import com.yf.pic.service.PluginService;
import com.yf.system.mapper.SysUserMapper;
import com.yf.system.service.SysUserService;
import com.yf.test.util.BaseTest;

public class TestServiceTest extends BaseTest{

	 @Autowired 
	 private SysUserMapper mapper;        
	 @Autowired 
	 private SysUserService service;        
	 @Autowired 
	 private PluginService pluginService;        
	 @Autowired 
	 private UserPluginMapper userPluginService;        
	 
	 @Test
	 public void mapperTest() {
		mapper.countByCondition(null);
		service.countByCondition(null);
		mapper.findEntitysByCondition(null);
	    Map<String,Object>map1 = new HashMap<String,Object>();
	    map1.put("pluginName", "3D旋转");
	    List<Plugin>pluginList = pluginService.findEntitysByCondition(map1);
	    userPluginService.countByCondition(null);
	 }
}
