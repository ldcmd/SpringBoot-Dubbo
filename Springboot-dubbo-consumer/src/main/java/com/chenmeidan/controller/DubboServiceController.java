package com.chenmeidan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenmeidan.service.DubboServiceConsumer;

@Controller
public class DubboServiceController {

	@Autowired
	private DubboServiceConsumer dubboServiceConsumer;

	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return dubboServiceConsumer.getString();
	}

}
