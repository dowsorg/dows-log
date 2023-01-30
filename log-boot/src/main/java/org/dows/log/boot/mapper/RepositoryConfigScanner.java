//package org.dows.log.boot.mapper;
//
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.mapper.MapperScannerConfigurer;
//import org.reflections.Reflections;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.util.Set;
//
//public class RepositoryConfigScanner {
//
//    @Value("${mybatis-plus.scanner.base.package}")
//    private String basePackage;
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource);
//        sessionFactoryBean.setMapperLocations(getResources());
//        return sessionFactoryBean.getObject();
//    }
//
//    @Bean
//    public MapperScannerConfigurer getMapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setAnnotationClass(Repository.class);
//        mapperScannerConfigurer.setBasePackage(basePackage);
//        return mapperScannerConfigurer;
//    }
//
//    public Resource[] getResources() throws IOException {
//        Resource monthlyResources[] = new Resource[0];
//        Reflections reflections = new Reflections(basePackage);
//        Set<Class<? extends RepositoryProvider>> monitorClasses = reflections.getSubTypesOf(RepositoryProvider.class);
//        for (Class<? extends RepositoryProvider> monitor : monitorClasses) {
//            RepositoryAnnotation annotation = monitor.getAnnotation(RepositoryAnnotation.class);
//            for (String resource : annotation.resources()) {
//                monthlyResources = (Resource[]) ArrayUtils.addAll(monthlyResources, new PathMatchingResourcePatternResolver().getResources(resource));
//            }
//        }
//        return monthlyResources;
//    }
//}
