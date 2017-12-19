package com.net.esdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/***
 * 
 * @author  作者 E-mail: issacgo@outlook.com
 * @date 创建时间：2017年12月19日 下午12:00:00
 * @explain index
 * @return
 *
 */
@Controller
public class IndexController {
	protected final Logger logger = LoggerFactory.getLogger(IndexController.class);
	/**
	 * index
	 * @return
	 */
    @RequestMapping("/index")
    public String index() {
    	
        return "/index";
        
    }
}