package com.chenmeidan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chenmeidan.service.DubboService;

/**
 * 创建服务接口实现类
 * @author liudan
 *
 */
@Service  //声明为一个dubbo服务
public class DubboServiceImpl implements DubboService {

	public String getString() {
		return "Hello dubbo provider";
	}

}
