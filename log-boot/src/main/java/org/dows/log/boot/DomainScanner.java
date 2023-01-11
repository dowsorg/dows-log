package org.dows.log.boot;

import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.classreading.MetadataReader;

import java.util.Set;

public class DomainScanner extends ClassPathBeanDefinitionScanner {
    public DomainScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    @Override
    protected boolean isCandidateComponent(MetadataReader metadataReader) {
        return true;
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            String beanClassName = beanDefinition.getBeanClassName();
            Class<?> entityClazz = null;
            try {
                entityClazz = Class.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(beanClassName);
            }

//            DynamicMapperCreator dynamicMapperCreator = new DynamicMapperCreator();
//            Class<?> mapperClazz = dynamicMapperCreator.getOrCreateMapperClazz(entityClazz);
//            beanDefinition.setBeanClass(MapperFactoryBean.class);
//            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
//            constructorArgumentValues.addIndexedArgumentValue(0, mapperClazz);
//            beanDefinition.setConstructorArgumentValues(constructorArgumentValues);
//            beanDefinition.getPropertyValues().add("sqlSessionFactory",
//                    new RuntimeBeanReference("sqlSessionFactory"));
        }

        return beanDefinitionHolders;
    }
}

