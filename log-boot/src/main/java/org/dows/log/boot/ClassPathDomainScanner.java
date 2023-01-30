package org.dows.log.boot;

import com.baomidou.mybatisplus.annotation.TableName;
import org.dows.log.api.DomainContextHolder;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class ClassPathDomainScanner extends ClassPathBeanDefinitionScanner {
    private final ClassLoader classLoader;

    public ClassPathDomainScanner(BeanDefinitionRegistry registry, ClassLoader classLoader) {
        super(registry, false);
        this.classLoader = classLoader;
    }

    public void registerFilters() {
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(TableName.class);
        this.addIncludeFilter(annotationTypeFilter);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        // todo 不是接口,且 implements CrudEntity
        if (!beanDefinition.getMetadata().isInterface()) {
            return true;
        }
        return false;
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);

        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            Class<?> beanClass = beanDefinition.getBeanClass();
            DomainContextHolder.put(beanClass);
            /**
             * 动态创建mapper
             */
//            DynamicMapperCreator dynamicMapperCreator = new DynamicMapperCreator();
//            Class<?> mapperClazz = dynamicMapperCreator.getOrCreateMapperClazz(entityClazz);
//            beanDefinition.setBeanClass(MapperFactoryBean.class);
//            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
//            constructorArgumentValues.addIndexedArgumentValue(0, mapperClazz);
//            beanDefinition.setConstructorArgumentValues(constructorArgumentValues);
//            beanDefinition.getPropertyValues().add("sqlSessionFactory", new RuntimeBeanReference("sqlSessionFactory"));
        }

        return beanDefinitionHolders;
    }
}

