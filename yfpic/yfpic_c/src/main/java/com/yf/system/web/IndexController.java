package com.yf.system.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yf.system.entity.SysUser;
import com.yf.system.service.SysMenuService;
import com.yf.system.service.SysUserService;
import com.yf.util.MD5Encoder;
import com.yf.web.BaseController;
@Controller
@Scope("session")
@RequestMapping(value = "/index")
public class IndexController extends BaseController {
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysUserService sysUserService;
	@RequestMapping(value = "tree")
	public void index(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		SysUser user = (SysUser)session.getAttribute("user");
		String menuTree = sysMenuService.listUserTree(Long.valueOf(user.getUserId()),false);
		writeToPage(menuTree, response);
	}
	@RequestMapping(value = "password")
	public String password(){
		return "password";
	}
	@RequestMapping(value = "checkUserPwd")
	public void checkUserPwd(HttpServletRequest request,PrintWriter out){
		SysUser sUser = (SysUser)request.getSession().getAttribute("user");
		String password = MD5Encoder.encode(request.getParameter("password"));
		String msg = "操作成功";
		boolean result = false;
		if(!password.equals(sUser.getUserPwd())){
			result =true;
			msg = "原始密码不正确";
		}
		ajaxJsonResponse(out, result, msg);
	}
	@RequestMapping(value = "update")
	public String update(HttpServletRequest request){
		String userPwd = request.getParameter("newPwd");
		SysUser sUser = (SysUser)request.getSession().getAttribute("user");
		sUser.setUserPwd(userPwd);
		sysUserService.updateUserPwd(sUser);
		return "success";
	}
}
