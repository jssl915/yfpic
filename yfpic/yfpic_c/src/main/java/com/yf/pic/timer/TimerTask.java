package com.yf.pic.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimerTask {
	Logger logger = LoggerFactory.getLogger(TimerTask.class);
	@Scheduled(fixedRate=2000)  // 每两秒执行一次
    public void scheduleMethod(){   
       //logger.info("每两秒执行一次..");
    }   

}
