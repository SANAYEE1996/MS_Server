package com.ms.config;

import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLTemplates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {
    @Bean
    public SQLTemplates sqlTemplates(){
        return new MySQLTemplates();
    }
}
