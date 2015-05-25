package com.yf.pic;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cykj.grcloud.mybatis.page.PageObject;
import com.yf.pic.entity.PicUser;
import com.yf.pic.entity.Plugin;
import com.yf.pic.entity.UserPlugin;
import com.yf.pic.service.PicUserService;
import com.yf.pic.service.PluginService;
import com.yf.pic.service.UserPluginService;
import com.yf.system.service.SysDictService;
import com.yf.util.JSONUtils;
import com.yf.web.BaseController;
/*
 * 用户组件图片管理
 */
@Controller
@Scope("session")
@RequestMapping(value = "/pic_user_plugin")
public class PicUserPluginController extends BaseController {
	Logger log = LoggerFactory.getLogger(PicUserPluginController.class);
	
	@Autowired
	private PicUserService picUserService;
	@Autowired
	private PluginService pluginService;
	@Autowired
	private UserPluginService userPluginService;
	@Autowired
	private SysDictService sysDictService;
	@RequestMapping(value = "init")
	public String init(HttpServletRequest request){
		String pUserId = request.getParameter("pUserId");
		PicUser pUser = picUserService.findById(Long.valueOf(pUserId));
		request.setAttribute("pUser", pUser);
		Map<String,String>statusMap = sysDictService.getDetailValueMap("状态");
		request.setAttribute("statusMap", JSONUtils.toJson(statusMap));
		request.setAttribute("statusCombo", JSONUtils.toCombo(statusMap));
		return "pic/user_plugin/init";
	}
	
	@RequestMapping(value = "list")
	public void list(HttpServletRequest request,HttpServletResponse response,UserPlugin userPlugin) {
		log.debug("method: list() ");
		PageObject po = getPageObject(request,"PLUGIN_ORDER asc,CREATE_TIME desc");
		po.getCondition().put("picStatus", userPlugin.getPicStatus());
		po.getCondition().put("pUserId",userPlugin.getPUserId());
		po.getCondition().put("pluginNameLike",userPlugin.getPluginName());
		po.getCondition().put("createTimeStart",request.getParameter("createTimeStart"));
		po.getCondition().put("createTimeEnd",request.getParameter("createTimeEnd"));
		po.getCondition().put("updateTimeStart",request.getParameter("updateTimeStart"));
		po.getCondition().put("updateTimeEnd",request.getParameter("updateTimeEnd"));
		writeToPage(JSONUtils.toJson(userPluginService.getGridDataModelByCondition(po)), response);
	}
	@RequestMapping(value = "showAdd")
	public String showAdd(HttpServletRequest request){
		String pUserId = request.getParameter("pUserId");
		PicUser pUser = picUserService.findById(Long.valueOf(pUserId));
		request.setAttribute("pUser", pUser);
		Map<String,Object>map = new HashMap<String,Object>();
		map.put("picStatus", 1);
		List<Plugin>pluginList = pluginService.findEntitysByCondition(map);
		Map<String,String>pMap = new HashMap<String,String>();
		for(Plugin p:pluginList){
			pMap.put(p.getPluginId().toString(),p.getPluginName());
		}
		request.setAttribute("pluginCombo", JSONUtils.toCombo(pMap));
		return "pic/user_plugin/add";
	}
	@RequestMapping(value = "insert")
	public String insert(HttpServletRequest request,UserPlugin userPlugin){
		userPlugin.setCreateTime(new Date());
		userPlugin.setPicStatus(1);
		userPluginService.insert(userPlugin);
		return "success";
	}
	@RequestMapping(value = "showEdit")
	public String showEdit(HttpServletRequest request){
		Map<String,String>statusMap = sysDictService.getDetailValueMap("状态");
		request.setAttribute("statusCombo", JSONUtils.toCombo(statusMap));
		Map<String,Object>map = new HashMap<String,Object>();
		map.put("picStatus", 1);
		List<Plugin>pluginList = pluginService.findEntitysByCondition(map);
		Map<String,String>pMap = new HashMap<String,String>();
		for(Plugin p:pluginList){
			pMap.put(p.getPluginId().toString(),p.getPluginName());
		}
		request.setAttribute("pluginCombo", JSONUtils.toCombo(pMap));
		Long userPluginId = Long.valueOf(request.getParameter("userPluginId"));
		UserPlugin userPlugin = userPluginService.findById(userPluginId);
		request.setAttribute("userPlugin", userPlugin);
		Integer userId = userPlugin.getPUserId();
		PicUser pUser = picUserService.findById(Long.valueOf(userId));
		request.setAttribute("pUser", pUser);
		return "pic/user_plugin/edit";
	}
	
	@RequestMapping(value = "update")
	public String update(HttpServletRequest request,UserPlugin userPlugin){
		userPluginService.mergeById(userPlugin);
		return "success";
	}
	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,PrintWriter out){
		log.debug("method: delete() ");
		String msg = "操作成功";
		boolean result = true;
		try {
			String ids = request.getParameter("ids");
			String[] userIds = ids.split(",");
			for(String userId : userIds){
				userPluginService.removeById(Long.valueOf(userId));
			}
		} catch (Exception e) {
			msg = "系统发生异常！";
			result = false;
		}
		ajaxJsonResponse(out, result, msg);
	}
}
