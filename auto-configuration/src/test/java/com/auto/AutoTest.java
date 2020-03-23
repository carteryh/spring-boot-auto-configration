package com.auto;

//import com.alibaba.fastjson.JSON;
import com.auto.configuration.User;
import com.starter.FormatTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootStarterDemoApplication.class) // 指定spring-boot的启动类
public class AutoTest {

    @Autowired
    private FormatTemplate formatTemplate;

    @Test
    public void test1() throws Exception {
        User user=new User();
        user.setAge(18);
        user.setName("chen");
        String s = formatTemplate.doFormat(user);
        System.out.println("=========");
        System.out.println(s);

//        JSON.parseObject()
    }

}
