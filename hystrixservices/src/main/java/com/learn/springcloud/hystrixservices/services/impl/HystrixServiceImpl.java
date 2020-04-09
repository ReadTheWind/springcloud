package com.learn.springcloud.hystrixservices.services.impl;

import com.learn.springcloud.common.domain.Result;
import com.learn.springcloud.common.domain.User;
import com.learn.springcloud.hystrixservices.services.HystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author liuhuan
 * @Desc
 * @time 2020/4/9 10:19
 */
@Service
public class HystrixServiceImpl implements HystrixService {

    private RestTemplate restTemplate;

    @Autowired
    public HystrixServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${service.url.user-services-client}")
    private String userServiceUrl;

    
    @HystrixCommand(fallbackMethod = "defaultUser")
    @Override
    public Result getAllUser() {
        Result re = restTemplate.getForObject(userServiceUrl+"/user/getUser", Result.class);
        System.out.println(re);
        return re;
    }

    public Result defaultUser() {
        User user = new User();
        user.setUserName("默认用户");
        user.setPassWord("123456");
        return Result.success("hystrix 断路默认返回！", user);
    }
}
