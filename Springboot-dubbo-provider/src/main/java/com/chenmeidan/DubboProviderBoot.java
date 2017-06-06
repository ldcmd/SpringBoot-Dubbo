package com.chenmeidan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication//配置在类路径中对象
@ImportResource({"classpath:dubbo-provider.xml"})//加载dubbo配置文件
public class DubboProviderBoot {
	/**
	 * 用于启动程序
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(DubboProviderBoot.class, args);
	}

}
