package org.dows.demo;

import org.dows.log.boot.EnableDomainMapping;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: lujl
 * @Date: 2023\1\6 0006 15:59
 */
@SpringBootApplication
@MapperScan(basePackages = {"org.dows.**.mapper"})
@EnableDomainMapping(domainPackages = {"org.dows.demo.entity","org.dows.demo.listener"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
