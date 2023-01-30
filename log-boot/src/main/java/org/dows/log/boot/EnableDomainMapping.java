package org.dows.log.boot;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DomainScannerRegistrar.class)
public @interface EnableDomainMapping {
    String[] domainPackages();
}
