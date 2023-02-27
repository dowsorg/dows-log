package org.dows.log.api.annotation;

import org.springframework.stereotype.Component;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Binlog {
    String hostName();
    String database();
    String table();
    Class tableSchemaClass();
}
