package com.yf.pic.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cykj.grcloud.mybatis.GenericBase.BaseServiceImpl;
import com.cykj.grcloud.mybatis.page.GridDataModel;
import com.cykj.grcloud.mybatis.page.PageObject;
import com.yf.pic.entity.UserPlugin;
import com.yf.pic.mapper.UserPluginMapper;

@Service
@Transactional
public class UserPluginServiceImpl extends BaseServiceImpl<UserPlugin, Long>
		implements UserPluginService {
	@Autowired
	UserPluginMapper mapper;
	@Autowired
	public void setMapper(UserPluginMapper mapper) {
		setGenericMapper(mapper);
	}

	/**
	 * 根据条件查询实体
	 * 
	 * @param condititon
	 * @return
	 */
	@Override
	public List<UserPlugin> findEntitysByCondition(Map<String, Object> condititon) {
		return mapper.selectEntitysByCondition(condititon);
	}

	/**
	 * 根据条件查询返回grid模块需要的数据
	 * 
	 * @param condititon
	 * @return
	 */
	@Override
	public GridDataModel getGridDataModelByCondition(PageObject po) {
		Integer totalCount = mapper.getCountByCondition(po.getCondition());
		RowBounds rowBounds = new RowBounds(po.getOffset(), po.getPageSize());
		List<UserPlugin> results = mapper.selectEntitysByCondition(
				po.getCondition(), rowBounds);
		return new GridDataModel(results, totalCount);
	}
}
