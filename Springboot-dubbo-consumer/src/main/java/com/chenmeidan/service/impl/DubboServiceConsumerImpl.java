package com.chenmeidan.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chenmeidan.service.DubboService;
import com.chenmeidan.service.DubboServiceConsumer;

/**
 * 创建服务接口实现类
 * @author liudan
 *
 */
@Service  
public class DubboServiceConsumerImpl implements DubboServiceConsumer {

	@Reference
	private  DubboService dubboService;
	
	public String getString() {
		return dubboService.getString();
	}

}
