package com.yf.pic.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  
@RequestMapping(value = "/hello")
public class HelloContorller {  
    private int index=0;  
    Logger logger=Logger.getLogger(HelloContorller.class.getName());  
      
    @RequestMapping(value="/init")  
    public void hello(){  
        logger.info("spring mvc hello world!"+index++);  
        return;  
    }  
}  