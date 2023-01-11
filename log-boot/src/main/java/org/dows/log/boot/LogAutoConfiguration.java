package org.dows.log.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"org.dows.log"})
@MapperScan(basePackages = {"org.dows.log.mapper"})
public class LogAutoConfiguration {

}
