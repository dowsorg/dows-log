package org.dows.log.boot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"org.dows.log.*"})
public class LogAutoConfiguration {

/*    @Configuration
    @Import({DomainScannerRegistrar.class})
    public static class RetrofitScannerRegistrarNotFoundConfiguration {

    }*/

}
