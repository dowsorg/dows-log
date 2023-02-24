package org.dows.log.boot;

import lombok.SneakyThrows;
import org.dows.log.api.DomainContextHolder;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.Table;
import java.util.Set;

public class ClassPathDomainScanner extends ClassPathBeanDefinitionScanner {
    private final ClassLoader classLoader;

    public ClassPathDomainScanner(BeanDefinitionRegistry registry, ClassLoader classLoader) {
        super(registry, false);
        this.classLoader = classLoader;
    }

    public void registerFilters() {
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(Table.class);
        this.addIncludeFilter(annotationTypeFilter);
//        this.addIncludeFilter(new AnnotationTypeFilter(Component.class));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        // todo 不是接口,且 implements CrudEntity
        if (!beanDefinition.getMetadata().isInterface()) {
            return true;
        }
        return false;
    }

    @SneakyThrows
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);

        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            beanDefinition.resolveBeanClass(classLoader);
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

