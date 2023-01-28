package org.dows.log;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DomainContextHolder {

    private static final Map<String, DomainMeta> DOMAIN_MAP = new HashMap<>();

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
            DomainMeta domainMeta = DomainMeta.builder()
                    .clazz(domainClass)
                    .tableName(tableName)
                    .fields(collect)
                    .build();
            DOMAIN_MAP.putIfAbsent(tableName, domainMeta);
        }


    }

    public static DomainMeta get(String domain) {


        return null;
    }
}
