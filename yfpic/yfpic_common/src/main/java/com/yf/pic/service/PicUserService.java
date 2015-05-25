package com.yf.pic.service;

import com.cykj.grcloud.mybatis.GenericBase.BaseService;
import com.cykj.grcloud.remote.service.RemoteService;
import com.yf.pic.entity.PicUser;

@RemoteService
public interface PicUserService extends BaseService<PicUser,Long>{
	
	public void updatePUserPwd(PicUser record);

}
