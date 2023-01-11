package org.dows.log.core;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.SneakyThrows;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogContext {

    private static Map<String, Class<? extends Model>> LogModelClassMap = new ConcurrentHashMap<>();

    public static <T extends Class<? extends Model>>void put(T modelClass){
        LogModelClassMap.put(modelClass.getClass().getSimpleName(),modelClass);
    }

    @SneakyThrows
    public static <T extends Model> T get(String modelName,Class<T> classT){
        Class<? extends Model> modelClass = LogModelClassMap.get(modelName);
        return (T) modelClass.getDeclaredConstructor().newInstance();

    }
}
