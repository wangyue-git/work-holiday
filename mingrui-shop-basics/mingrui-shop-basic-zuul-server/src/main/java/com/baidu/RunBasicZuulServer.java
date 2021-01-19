package com.baidu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
/**
 * @ClassName RunBasicZuulServer
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/19
 * @Version V1.0
 **/
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class RunBasicZuulServer {
    public static void main(String[] args) {
        SpringApplication.run(RunBasicZuulServer.class);
    }
}
