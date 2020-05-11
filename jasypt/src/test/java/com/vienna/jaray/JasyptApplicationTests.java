package com.vienna.jaray;

import com.vienna.jaray.config.JasyptStringEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class JasyptApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void jasyptEncrypt() {
        JasyptStringEncryptor jasyptStringEncryptor = new JasyptStringEncryptor("jaray");
        String en = jasyptStringEncryptor.encrypt("jdbc:mysql://192.168.230.128:3306/jaray-database?useUnicode=true&serverTimezone=GMT%2B8&characterEncoding=utf-8");
        String de = jasyptStringEncryptor.decrypt(en);
        log.info("加密后：{}", en);
        log.info("解密后：{}", de);
    }

}
