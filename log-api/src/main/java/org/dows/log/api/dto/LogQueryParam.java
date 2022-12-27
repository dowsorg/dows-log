package org.dows.log.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Getter
@Setter
public class LogQueryParam {

    //@Query(blurry = "username,description,address,requestIp,method,params")
    private String blurry;

    /**
     * 精确
     */
    //@Query
    private String logType;

    /**
     * BETWEEN
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@Query(type = Query.Type.BETWEEN)
    private List<Date> createTime;
}
