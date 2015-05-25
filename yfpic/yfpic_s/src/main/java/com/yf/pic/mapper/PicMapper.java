package com.yf.pic.mapper;

import java.util.Map;

import com.cykj.grcloud.mybatis.GenericBase.GenericMapper;
import com.yf.pic.entity.Pic;

public interface PicMapper extends GenericMapper<Pic,Long>{
	
	Integer removeByCondition(Map<String,Object>map);

}
