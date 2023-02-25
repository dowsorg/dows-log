package org.dows.log.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableFilter {
    private List<String> includes = new ArrayList<>();
    private List<String> excludes = new ArrayList<>();
}