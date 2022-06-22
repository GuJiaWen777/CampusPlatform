package com.ocean.platformspringboot;

import com.ocean.platformspringboot.service.ITaskService;
import com.ocean.platformspringboot.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("com.ocean.platformspringboot.service")
public class PlatformSpringbootApplicationTests {
    @Autowired
    private ITaskService taskService;
    @Test
    public void contextLoads() {

        taskService.deductPointForUser(10001,5);
    }

}
