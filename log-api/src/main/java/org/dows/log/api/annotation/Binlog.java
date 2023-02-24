package org.dows.log.api.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Component
public @interface Binlog {
    String hostName();
    String database();
    Class tableSchemaClass();
}
