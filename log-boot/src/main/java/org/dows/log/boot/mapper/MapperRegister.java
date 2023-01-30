//package org.dows.log.boot.mapper;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MapperRegister implements BeanDefinitionRegistryPostProcessor {
//    @Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
////        new DomainScanner(registry).scan("org.dows.*.entity");
//        new LogDomainScanner(registry).scan("org.dows.*.entity.log");
//    }
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//    }
//}