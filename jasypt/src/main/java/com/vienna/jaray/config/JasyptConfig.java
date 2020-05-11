package com.vienna.jaray.config;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class JasyptConfig {
	
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor(Environment environment){
        return new JasyptStringEncryptor(environment);
    }

}
