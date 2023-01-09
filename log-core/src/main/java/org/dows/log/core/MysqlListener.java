package org.dows.log.core;

import org.dows.log.api.BinlogListener;
import org.dows.log.api.annotation.Binlog;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class MysqlListener {
    private BinlogListener listener;
    private String hostName;
    private String database;
    private String table;
    private Class<?> entityClass;

    public MysqlListener(BinlogListener listener) {
        this.listener = listener;

        Class<?> targetClass = AopUtils.getTargetClass(listener);
        Binlog annotation = AnnotationUtils.findAnnotation(targetClass, Binlog.class);
        if (annotation == null)
            throw new RuntimeException("Mysql binlog listener必须添加 MysqlWatcher 注解");

        hostName = annotation.hostName();
        database = annotation.database();
        Class aClass = annotation.tableSchemaClass();
//        try {
//            aClass.getField("Table").getAnnotations();
//            table = aClass.getAnnotation("c").toString();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
        entityClass = getGenericClass(targetClass);
    }

    public BinlogListener getListener() {
        return listener;
    }

    public String getHostName() {
        return hostName;
    }

    public String getDatabase() {
        return database;
    }

    public String getTable() {
        return table;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    private Class<?> getGenericClass(Class<?> targetClass) {
        if (targetClass == Object.class)
            return null;

        Type[] types = targetClass.getGenericInterfaces();
        if (types.length == 0) {
            types = new Type[]{targetClass.getGenericSuperclass()};
        }

        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType t = (ParameterizedType) type;
                Type[] array = t.getActualTypeArguments();
                return (Class<?>) array[0];
            }
        }

        return getGenericClass(targetClass.getSuperclass());
    }
}
