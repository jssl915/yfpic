package com.yf.pic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yf.pic.entity.PicUser;
import com.yf.pic.entity.Plugin;
import com.yf.pic.entity.PluginPic;
import com.yf.pic.entity.UserPlugin;
import com.yf.pic.service.PicService;
import com.yf.pic.service.PicUserService;
import com.yf.pic.service.PluginPicService;
import com.yf.pic.service.PluginService;
import com.yf.pic.service.UserPluginService;
import com.yf.util.SysProperties;
import com.yf.web.BaseController;

/*
 * 组件
 */
@Controller
@Scope("session")
@RequestMapping(value = "/pic")
public class PluginController extends BaseController {
	Logger log = LoggerFactory.getLogger(PluginController.class);
	@Autowired
	private PicUserService picUserService;
	@Autowired
	private PluginService pluginService;
	@Autowired
	private UserPluginService userPluginService;
	@Autowired
	private PluginPicService pluginPicService;
	@Autowired
	private PicService picService;

	@RequestMapping(value = "3d")
	public String init(HttpServletRequest request){
		
		return "pic/3d/index";
	}
	@RequestMapping(value="/{urlName:[a-z-]+}")
	public String regularExpression(HttpServletRequest request,@PathVariable String urlName){
	    String pUserName = urlName;
	   //根据pUserName得到pUserId
	    Map<String,Object>map = new HashMap<String,Object>();
	    map.put("pUserName", pUserName);
	    List<PicUser>picUserList = picUserService.findEntitysByCondition(map);
	    if(picUserList.size()==0){return "pic/3d/index";}
	    
	    Integer pUserId = picUserList.get(0).getPUserId();
	    
	    //根据pluginName得到pluginId
	    Map<String,Object>map1 = new HashMap<String,Object>();
	    map1.put("pluginName", "3D旋转");
	    List<Plugin>pluginList = pluginService.findEntitysByCondition(map1);
	    if(pluginList.size()==0){return "pic/3d/index";}
	    Integer pluginId = pluginList.get(0).getPluginId();
	    
	    //根据pluginId和pUserId得到userPluginId
	    Map<String,Object>map2 = new HashMap<String,Object>();
	    map2.put("pluginId", pluginId);
	    map2.put("pUserId", pUserId);
	    List<UserPlugin>userPluginList = userPluginService.findEntitysByCondition(map2);
	    if(userPluginList.size()==0){return "pic/3d/index";}
	    Integer userPluginId = userPluginList.get(0).getUserPluginId();
	    
	    //根据userPluginId得到该用户下这种组件的图片
	    Map<String,Object>map3 = new HashMap<String,Object>();
	    map3.put("userPluginId", userPluginId);
	    map3.put("orderByClause", "PLUGIN_ORDER asc");
	    List<PluginPic>picList = pluginPicService.getAllbyCondition(map3);
	    if(picList.size()==0){return "pic/3d/index";}
	    request.setAttribute("picList", picList);
	    request.setAttribute("picIp",SysProperties.getProperty("PIC_IP"));
	    return "pic/3d/index";
	}
}
