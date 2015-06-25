package com.yf.pic.web;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cykj.grcloud.mybatis.page.PageObject;
import com.yf.pic.entity.PicUser;
import com.yf.pic.service.PicUserService;
import com.yf.system.service.SysDictService;
import com.yf.util.JSONUtils;
import com.yf.util.SysProperties;
import com.yf.web.BaseController;

/*
 * 用户管理
 */
@Controller
@RequestMapping(value = "/pic_user")
public class PicUserController extends BaseController {
	Logger log = LoggerFactory.getLogger(PicUserController.class);
	
	@Autowired
	private PicUserService picUserService;
	@Autowired
	private SysDictService sysDictService;
	
	
	@RequestMapping(value = "init")
	public String init(HttpServletRequest request){
		Map<String,String>statusMap = sysDictService.getDetailValueMap("状态");
		request.setAttribute("statusMap", JSONUtils.toJson(statusMap));
		request.setAttribute("statusCombo", JSONUtils.toCombo(statusMap));
		Map<String,String>sexMap = sysDictService.getDetailValueMap("性别");
		request.setAttribute("initPassword", SysProperties.getProperty("INIT_PWD"));
		request.setAttribute("sexMap", JSONUtils.toJson(sexMap));
		request.setAttribute("sexCombo", JSONUtils.toCombo(sexMap));
		return "pic/user/init";
	}
	
	@RequestMapping(value = "list")
	public void list(HttpServletRequest request,HttpServletResponse response,PicUser user) {
		log.debug("method: list() ");
		PageObject po = getPageObject(request,"P_USER_ORDER asc,CREATE_TIME desc");
		po.getCondition().put("pUserNameLike", user.getPUserName());
		po.getCondition().put("pRealNameLike", user.getPRealName());
		po.getCondition().put("pStatus", user.getPStatus());
		po.getCondition().put("createTimeStart",request.getParameter("createTimeStart"));
		po.getCondition().put("createTimeEnd",request.getParameter("createTimeEnd"));
		po.getCondition().put("updateTimeStart",request.getParameter("updateTimeStart"));
		po.getCondition().put("updateTimeEnd",request.getParameter("updateTimeEnd"));
		writeToPage(JSONUtils.toJson(picUserService.getGridDataModelByCondition(po)), response);
	}
	@RequestMapping(value = "showAdd")
	public String showAdd(HttpServletRequest request){
		Map<String,String>sexMap = sysDictService.getDetailValueMap("性别");
		request.setAttribute("sexCombo", JSONUtils.toCombo(sexMap));
		request.setAttribute("initPassword", SysProperties.getProperty("INIT_PWD"));
		return "pic/user/add";
	}
	@RequestMapping(value = "insert")
	public String insert(HttpServletRequest request,PicUser user){
		picUserService.insert(user);
		return "success";
	}
	@RequestMapping(value = "showEdit")
	public String showEdit(HttpServletRequest request){
		Long userId = Long.valueOf(request.getParameter("pUserId"));
		PicUser pUser = picUserService.findById(userId);
		request.setAttribute("pUser", pUser);
		Map<String,String>statusMap = sysDictService.getDetailValueMap("状态");
		request.setAttribute("statusCombo", JSONUtils.toCombo(statusMap));
		Map<String,String>sexMap = sysDictService.getDetailValueMap("性别");
		request.setAttribute("sexCombo", JSONUtils.toCombo(sexMap));
		return "pic/user/edit";
	}
	
	@RequestMapping(value = "update")
	public String update(HttpServletRequest request,PicUser user){
		picUserService.mergeById(user);
		return "success";
	}
	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,PrintWriter out){
		log.debug("method: delete() ");
		String ids = request.getParameter("ids");
		String[] userIds = ids.split(",");
		List<String>idList = Arrays.asList(userIds);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("idList", idList);
		picUserService.removeByCondition(map);
		ajaxJsonResponse(out, true, "操作成功");
	}
	
	@RequestMapping(value = "checkUserName")
	public void checkUserName(HttpServletRequest request,HttpServletResponse response,@ModelAttribute PicUser user) {
		Map<String, Object> condititon = new HashMap<String, Object>();
		condititon.put("pUserName", user.getPUserName());
		condititon.put("notPUserId", user.getPUserId());
		int flag = picUserService.countByCondition(condititon);
		writeToPage(flag>0?"false":"true", response);
	}
	
	@RequestMapping(value = "initPwd")
	public void initPwd(HttpServletRequest request,PrintWriter out){
		log.debug("method: initPwd() ");
		String msg = "操作成功";
		boolean result = true;
		try {
			String ids = request.getParameter("ids");
			String[] aId = ids.split(",");
			for(String id:aId){
				PicUser user = picUserService.findById(Long.valueOf(id));
				user.setPUserId(Integer.valueOf(id));
				user.setPUserPsd(SysProperties.getProperty("INIT_PWD"));
				user.setUpdateTime(new Date());
				picUserService.updatePUserPwd(user);
			}
		} catch (Exception e) {
			msg = "系统发生异常！";
			result = false;
		}
		ajaxJsonResponse(out, result, msg);
	}
}
