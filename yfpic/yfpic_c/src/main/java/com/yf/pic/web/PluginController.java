package com.yf.pic.web;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cykj.grcloud.mybatis.page.PageObject;
import com.yf.pic.entity.Plugin;
import com.yf.pic.service.PluginService;
import com.yf.system.service.SysDictService;
import com.yf.util.JSONUtils;
import com.yf.web.BaseController;

/*
 * 组件管理
 */
@Controller
@RequestMapping(value = "/pic_plugin")
public class PluginController extends BaseController {
	Logger log = LoggerFactory.getLogger(PluginController.class);
	
	@Autowired
	private PluginService pluginService;
	@Autowired
	private SysDictService sysDictService;
	@RequestMapping(value = "init")
	public String init(HttpServletRequest request){
		Map<String,String>statusMap = sysDictService.getDetailValueMap("状态");
		request.setAttribute("statusMap", JSONUtils.toJson(statusMap));
		request.setAttribute("statusCombo", JSONUtils.toCombo(statusMap));
		return "pic/plugin/init";
	}
	
	@RequestMapping(value = "list")
	public void list(HttpServletRequest request,HttpServletResponse response,Plugin plugin) {
		log.debug("method: list() ");
		PageObject po = getPageObject(request,"PLUGIN_ORDER asc,CREATE_TIME desc");
		po.getCondition().put("picStatus", plugin.getPicStatus());
		po.getCondition().put("pluginNameLike",plugin.getPluginName());
		po.getCondition().put("createTimeStart",request.getParameter("createTimeStart"));
		po.getCondition().put("createTimeEnd",request.getParameter("createTimeEnd"));
		po.getCondition().put("updateTimeStart",request.getParameter("updateTimeStart"));
		po.getCondition().put("updateTimeEnd",request.getParameter("updateTimeEnd"));
		writeToPage(JSONUtils.toJson(pluginService.getGridDataModelByCondition(po)), response);
	}
	@RequestMapping(value = "showAdd")
	public String showAdd(HttpServletRequest request){
		return "pic/plugin/add";
	}
	@RequestMapping(value = "insert")
	public String insert(HttpServletRequest request,Plugin plugin){
		plugin.setCreateTime(new Date());
		plugin.setPicStatus(1);
		pluginService.insert(plugin);
		return "success";
	}
	@RequestMapping(value = "showEdit")
	public String showEdit(HttpServletRequest request){
		Long pluginId = Long.valueOf(request.getParameter("pluginId"));
		Plugin plugin = pluginService.findById(pluginId);
		request.setAttribute("plugin", plugin);
		Map<String,String>statusMap = sysDictService.getDetailValueMap("状态");
		request.setAttribute("statusCombo", JSONUtils.toCombo(statusMap));
		return "pic/plugin/edit";
	}
	
	@RequestMapping(value = "update")
	public String update(HttpServletRequest request,Plugin plugin){
		plugin.setUpdateTime(new Date());
		pluginService.mergeById(plugin);
		return "success";
	}
	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,PrintWriter out){
		log.debug("method: delete() ");
		String msg = "操作成功";
		boolean result = true;
		try {
			String ids = request.getParameter("ids");
			String[] pluginIds = ids.split(",");
			for(String pluginId : pluginIds){
				pluginService.removeById(Long.valueOf(pluginId));
			}
		} catch (Exception e) {
			msg = "系统发生异常！";
			result = false;
		}
		ajaxJsonResponse(out, result, msg);
	}
	
	@RequestMapping(value = "checkPluginName")
	public void checkUserName(HttpServletRequest request,HttpServletResponse response,Plugin plugin) {
		Map<String, Object> condititon = new HashMap<String, Object>();
		condititon.put("pluginName", plugin.getPluginName());
		condititon.put("notPluginId", plugin.getPluginId());
		int flag = pluginService.countByCondition(condititon);
		writeToPage(flag>0?"false":"true", response);
	}
	
}
