package org.dows.log.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainMeta {
    private Class clazz;
    private String tableName;
    private List<String> fields;
}
