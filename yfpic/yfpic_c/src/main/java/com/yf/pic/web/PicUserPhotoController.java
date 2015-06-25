package com.yf.pic.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cykj.grcloud.util.Ognl;
import com.yf.pic.entity.Pic;
import com.yf.pic.entity.PicUser;
import com.yf.pic.service.PicService;
import com.yf.pic.service.PicUserService;
import com.yf.system.service.SysDictService;
import com.yf.util.FileUtil;
import com.yf.util.ImageUtils;
import com.yf.util.JSONUtils;
import com.yf.util.SysProperties;
import com.yf.web.BaseController;

/*
 * 用户图片管理
 */
@Controller
@RequestMapping(value = "/pic_user_photo")
public class PicUserPhotoController extends BaseController {
	Logger log = LoggerFactory.getLogger(PicUserPhotoController.class);
	
	@Autowired
	private PicUserService picUserService;
	@Autowired
	private PicService picService;
	@Autowired
	private SysDictService sysDictService;
	@RequestMapping(value = "add")
	public String init(HttpServletRequest request){
		String pUserId = request.getParameter("pUserId");
		PicUser pUser = picUserService.findById(Long.valueOf(pUserId));
		request.setAttribute("pUser", pUser);
		return "pic/user_photo/add";
	}
	@RequestMapping(value = "edit")
	public String edit(HttpServletRequest request){
		String pUserId = request.getParameter("pUserId");
		PicUser pUser = picUserService.findById(Long.valueOf(pUserId));
		request.setAttribute("pUser", pUser);
		HashMap<String,Object>map = new HashMap<String,Object>();
		map.put("picUserId", pUserId);
		map.put("picStatus", 1);
		map.put("orderByClause", "PIC_ORDER asc");
		List<Pic>picList = picService.findEntitysByCondition(map);
		request.setAttribute("picList", JSONUtils.toJson(picList));
		request.setAttribute("picIp",SysProperties.getProperty("PIC_IP"));
		return "pic/user_photo/edit";
	}
	
	@RequestMapping(value = "insert")
	public void insert(HttpServletRequest request,PrintWriter out){
		String msg = "添加成功";
		String imgSrcs = request.getParameter("imgSrc");
		Integer picUserId = Integer.valueOf(request.getParameter("picUserId"));
		String aImgSrc[] = imgSrcs.split(",");
		String url = SysProperties.getProperty("PIC_LOCAL");
		String copy_url = SysProperties.getProperty("PIC_LOCAL_COPY");
		for(int i=0;i<aImgSrc.length;i++){
			Pic pic = new Pic();
			pic.setPicUserId(picUserId);
			pic.setPicUrl(aImgSrc[i]);
			pic.setPicStatus(1);
			pic.setPicOrder(i+1);
			pic.setCreateTime(new Date());
			picService.insert(pic);
			//将copy_pic中图片存到pic
			String img[] =  aImgSrc[i].split("\\.");
			File targetFile = new File(copy_url, aImgSrc[i]);
			FileUtil.copyFile(targetFile,url+"/"+aImgSrc[i].split("/")[0],url+"/"+aImgSrc[i]);
		
			String scaleImg = url+"/"+img[0]+"_scale."+img[1];
			ImageUtils.scale3(url+"/"+aImgSrc[i], scaleImg, 200, false);// 测试OK
		}
		ajaxJsonResponse(out, true, msg);
	}
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,PrintWriter out){
		String msg = "编辑成功";
		String picIds = request.getParameter("aPicId");
		String pUserId = request.getParameter("pUserId");
		String aPicId[] = picIds.split(",");
		
		//删除文件夹里的图片
		//1先查找需要删除的图片的pic_url
		HashMap<String,Object>map = new HashMap<String,Object>();
		map.put("picUserId",pUserId);
		map.put("notInPicIdList", Arrays.asList(aPicId));
		List<Pic>delPicList = picService.findEntitysByCondition(map);
		String url = SysProperties.getProperty("PIC_LOCAL");
		for(Pic p:delPicList){
			FileUtil.deleteFile(url,p.getPicUrl());
		}
		//删除picIds没有的
		picService.removeByCondition(map);
		if(Ognl.isNotEmpty(picIds)){
			for(int i=0;i<aPicId.length;i++){
				Pic pic = new Pic();
				pic.setPicId(Integer.valueOf(aPicId[i]));
				pic.setPicOrder(i+1);
				picService.mergeById(pic);
			}
		}
		ajaxJsonResponse(out, true, msg);
	}
	
	@RequestMapping(value = "uploadDo")
	public void uploadDo(HttpServletRequest request,HttpServletResponse response){
		String pUserName = request.getParameter("pUserName");
		String copy_url = SysProperties.getProperty("PIC_LOCAL_COPY")+ pUserName;
		String picIpCopy = SysProperties.getProperty("PIC_IP_COPY");
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multiRequest.getFile("file");
		String fileName = multipartFile.getOriginalFilename();
		//文件重命名
		String file [] = fileName.split("\\.");
		Random rand=new Random();
		int a=rand.nextInt(99999999);
		String filePrefix = String.valueOf(a);
		String fileSufix = file[1];
		fileName = filePrefix+"."+fileSufix ;
		try {
			//上传时先存到copy_url,点保存时再存到url
			FileUtil.transferMultipartFile(multipartFile,copy_url, fileName);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fileUrl", picIpCopy+pUserName);
		map.put("fileName", fileName);
		writeToPage(JSONUtils.toJson(map), response);
	}
}
