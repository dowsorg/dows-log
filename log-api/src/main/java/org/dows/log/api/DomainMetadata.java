package org.dows.log.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainMetadata {
    private Class clazz;
    private String tableName;
    private final List<String> fields = new ArrayList<>();
    private final Map<String, Object> fieldValues = new ConcurrentHashMap<>();

    public DomainMetadata addFields(String field) {
        fields.add(field);
        return this;
    }

    public DomainMetadata setFieldValue(String field, Object value) {
        if (fields.contains(field)) {
            fieldValues.put(field, value);
        }
        return this;
    }

    public InsertWrapper insertWrapper() {
        InsertWrapper insertWrapper = new InsertWrapper();
        insertWrapper.setMap(fieldValues);
        return insertWrapper;
    }
}
