package org.dows.demo;

import org.dows.demo.entity.UserEntity;
import org.dows.demo.entity.log.UserEntityLog;
import org.dows.log.boot.EnableDomainMapping;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author: lujl
 * @Date: 2023\1\6 0006 15:59
 */
@SpringBootApplication
//@ComponentScan(basePackages = {"org.dows.log"})
@MapperScan(basePackages = {"org.dows.**.mapper"})
//@EntityScan(basePackageClasses = {UserEntity.class, UserEntityLog.class})
@EnableDomainMapping(domainPackages = "")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
