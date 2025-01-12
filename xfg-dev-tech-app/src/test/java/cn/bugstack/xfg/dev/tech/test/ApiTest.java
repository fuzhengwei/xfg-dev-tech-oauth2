package cn.bugstack.xfg.dev.tech.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void test_passwordEncoder() {
        log.info("测试结果:{}", passwordEncoder.encode("123456"));
    }

}
