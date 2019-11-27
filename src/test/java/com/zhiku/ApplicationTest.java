package com.zhiku;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ElearningApplication.class})
//由于是web项目，JUnit需要模拟ServletContext，所以需要加上@WebAppConfiguration
@WebAppConfiguration
//指定每个单元中的测试方法按字母顺序执行(若写成MethodSorters.DEFAULT的话，将是一个确定的，但不可预知的顺序)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTest {
    @Before
    public void init(){
        System.out.println("------------------开始测试--------------------");
    }

    @After
    public void after(){
        System.out.println("------------------结束测试--------------------");
    }
}
