package org.dows.log.api;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DomainContextHolder {

    private static final Map<String, DomainMetadata> DOMAIN_MAP = new HashMap<>();


    private static final Map<Class<?>, DomainMetadata> DOMAIN_CLASS_MAP = new HashMap<>();
    /**
     * 初始化时扫描装入
     *
     * @param domainClass
     */
    public static void put(Class<?> domainClass) {
        TableName annotation = AnnotationUtil.getAnnotation(domainClass, TableName.class);
        if (annotation != null) {
            String tableName = annotation.value();
            List<String> collect = Arrays.stream(domainClass.getDeclaredFields())
                    .map(f -> StrUtil.toUnderlineCase(f.getName()))
                    .collect(Collectors.toList());
            DomainMetadata domainMetadata = DomainMetadata.builder()
                    .clazz(domainClass)
                    .tableName(tableName)
                    //.fields(collect)
                    .build();
            for (String s : collect) {
                domainMetadata.addFields(s);
            }
            DOMAIN_CLASS_MAP.putIfAbsent(domainClass,domainMetadata);
            DOMAIN_MAP.putIfAbsent(tableName, domainMetadata);
        }
    }

    public static DomainMetadata get(String domain) {
        DomainMetadata domainMetadata = DOMAIN_MAP.get(domain);
        if (domainMetadata == null) {
            throw new RuntimeException("不存在该domain");
        }
        return domainMetadata;
    }

    public static DomainMetadata get(Class domainClass) {
        DomainMetadata domainMetadata = DOMAIN_CLASS_MAP.get(domainClass);
        if (domainMetadata == null) {
            throw new RuntimeException("不存在该domain");
        }
        return domainMetadata;
    }
}
