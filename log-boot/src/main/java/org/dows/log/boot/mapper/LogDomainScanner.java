//package org.dows.log.boot.mapper;
//
//import cn.hutool.extra.spring.SpringUtil;
//import lombok.SneakyThrows;
//import org.dows.log.api.annotation.Binlog;
//import org.dows.log.core.LogContext;
//import org.mybatis.spring.mapper.MapperFactoryBean;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.config.BeanDefinitionHolder;
//import org.springframework.beans.factory.config.ConstructorArgumentValues;
//import org.springframework.beans.factory.config.RuntimeBeanReference;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
//import org.springframework.context.annotation.ScannedGenericBeanDefinition;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.core.type.classreading.MetadataReader;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//
//public class LogDomainScanner extends ClassPathBeanDefinitionScanner {
//    public LogDomainScanner(BeanDefinitionRegistry registry) {
//        super(registry, false);
//    }
//
//    @Override
//    protected boolean isCandidateComponent(MetadataReader metadataReader) {
//        return true;
//    }
//
//    @SneakyThrows
//    @Override
//    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
//        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
//        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
//
//            ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
//            Class beanClass = beanDefinition.getBeanClass();
//            Binlog annotation = AnnotationUtils.findAnnotation(beanClass, Binlog.class);
//            if(annotation!= null){
//                LogContext.put(beanClass);
//            }
//        }
//        return beanDefinitionHolders;
//    }
//}
//
