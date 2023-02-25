package org.dows.log.config;

import lombok.Data;

@Data
public class TableFilter {
    private String includes;
    private String excludes;
}