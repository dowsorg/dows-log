package org.dows.demo;

import org.dows.log.boot.EnableDomainMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: lujl
 * @Date: 2023\1\6 0006 15:59
 */
@SpringBootApplication
//@ComponentScan(basePackages = {"org.dows.log"})
//@MapperScan(basePackages = {"org.dows.**.mapper"})
//@EntityScan(basePackageClasses = {UserEntity.class, UserEntityLog.class})
@EnableDomainMapping(domainPackages = "org.dows.demo.entity")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
