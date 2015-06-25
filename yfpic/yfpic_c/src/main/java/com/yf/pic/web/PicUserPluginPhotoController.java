package com.yf.pic.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cykj.grcloud.util.Ognl;
import com.yf.pic.entity.Pic;
import com.yf.pic.entity.PicUser;
import com.yf.pic.entity.PluginPic;
import com.yf.pic.entity.UserPlugin;
import com.yf.pic.service.PicService;
import com.yf.pic.service.PicUserService;
import com.yf.pic.service.PluginPicService;
import com.yf.pic.service.UserPluginService;
import com.yf.system.service.SysDictService;
import com.yf.util.JSONUtils;
import com.yf.util.SysProperties;
import com.yf.web.BaseController;
/*
 * 用户组件图片管理
 */
@Controller
@RequestMapping(value = "/pic_user_plugin_photo")
public class PicUserPluginPhotoController extends BaseController {
	Logger log = LoggerFactory.getLogger(PicUserPluginPhotoController.class);
	@Autowired
	private PicService picService;
	@Autowired
	private PicUserService picUserService;
	@Autowired
	private PluginPicService pluginPicService;
	@Autowired
	private UserPluginService userPluginService;
	@Autowired
	private SysDictService sysDictService;
	@RequestMapping(value = "add")
	public String init(HttpServletRequest request){
		String userPluginId = request.getParameter("userPluginId");
		Map<String,Object>pluginMap = new HashMap<String,Object>();
		pluginMap.put("userPluginId", userPluginId);
		//得到该用户组件已绑定的图片
		List<PluginPic>pluginPicList = pluginPicService.findEntitysByCondition(pluginMap);
		UserPlugin userPlugin = userPluginService.findById(Long.valueOf(userPluginId));
		
		Map<String,Object>picMap = new HashMap<String,Object>();
		picMap.put("picUserId", userPlugin.getPUserId());
		picMap.put("orderByClause", "PIC_ORDER asc");
		//得到该用户所有的图片
		List<Pic>picList = picService.findEntitysByCondition(picMap);
		request.setAttribute("userPicList", picList);
		request.setAttribute("pluginPicListJson", JSONUtils.toJson(pluginPicList));
		PicUser pUser = picUserService.findById(Long.valueOf(userPlugin.getPUserId()));
		request.setAttribute("pUser", pUser);
		request.setAttribute("picIp",SysProperties.getProperty("PIC_IP"));
		request.setAttribute("userPluginId", userPluginId);
		return "pic/user_plugin_photo/add";
	}
	@RequestMapping(value = "insert")
	public void insert(HttpServletRequest request,PrintWriter out){
		String msg = "添加成功";
		String picIds = request.getParameter("aPicId");
		Integer userPluginId = Integer.valueOf(request.getParameter("userPluginId"));
		String aPicId[] = picIds.split(",");
		Map<String,Object>condition = new HashMap<String,Object>();
		condition.put("userPluginId", userPluginId);
		pluginPicService.deleteByCondition(condition);
		for(int i=0;i<aPicId.length;i++){
			PluginPic pluginPic = new PluginPic();
			pluginPic.setUserPluginId(userPluginId);
			pluginPic.setPicId(Integer.valueOf(aPicId[i]));
			pluginPic.setPluginOrder(i+1);
			pluginPic.setCreateTime(new Date());
			pluginPicService.insert(pluginPic);
		}
		ajaxJsonResponse(out, true, msg);
	}
	@RequestMapping(value = "edit")
	public String edit(HttpServletRequest request){
		String userPluginId = request.getParameter("userPluginId");
		Map<String,Object>pluginMap = new HashMap<String,Object>();
		pluginMap.put("userPluginId", userPluginId);
		
		//得到该用户组件已绑定的图片
		List<PluginPic>pluginPicList = pluginPicService.findEntitysByCondition(pluginMap);
		UserPlugin userPlugin = userPluginService.findById(Long.valueOf(userPluginId));
		
		Map<String,Object>picMap = new HashMap<String,Object>();
		picMap.put("picUserId", userPlugin.getPUserId());
		picMap.put("orderByClause", "PIC_ORDER asc");
		//得到该用户所有的图片
		List<Pic>newPicList = new ArrayList<Pic>();
		List<Pic>picList = picService.findEntitysByCondition(picMap);
		for(Pic pic :picList){
			for(PluginPic ppic:pluginPicList){
				if(pic.getPicId().equals(ppic.getPicId())){
					newPicList.add(pic);
				}
			}
		}
		request.setAttribute("pluginPicListJson", JSONUtils.toJson(newPicList));
		PicUser pUser = picUserService.findById(Long.valueOf(userPlugin.getPUserId()));
		request.setAttribute("pUser", pUser);
		request.setAttribute("picIp",SysProperties.getProperty("PIC_IP"));
		request.setAttribute("userPluginId", userPluginId);
		return "pic/user_plugin_photo/edit";
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,PrintWriter out){
		String msg = "编辑成功";
		String picIds = request.getParameter("aPicId");
		String userPluginId = request.getParameter("userPluginId");
		Map<String,Object>condition = new HashMap<String,Object>();
		condition.put("userPluginId", userPluginId);
		pluginPicService.deleteByCondition(condition);
		String aPicId[] = picIds.split(",");
		if(Ognl.isNotEmpty(picIds)){
			for(int i=0;i<aPicId.length;i++){
				PluginPic pluginPic= new PluginPic();
				pluginPic.setUserPluginId(Integer.valueOf(userPluginId));
				pluginPic.setPicId(Integer.valueOf(aPicId[i]));
				pluginPic.setPluginOrder(i+1);
				pluginPic.setCreateTime(new Date());
				pluginPicService.insert(pluginPic);
			}
		}
		ajaxJsonResponse(out, true, msg);
	}
}
