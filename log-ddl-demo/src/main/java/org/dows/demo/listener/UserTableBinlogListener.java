package org.dows.demo.listener;

import org.dows.demo.entity.UserEntity;
import org.dows.demo.entity.log.UserBinLog;
import org.dows.log.api.BinlogListener;
import org.dows.log.api.DomainContextHolder;
import org.dows.log.api.DomainMetadata;
import org.dows.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2023/2/23 17:01
 */
@Component
public class UserTableBinlogListener implements BinlogListener<UserEntity> {

    @Autowired
    LogService logService;
    @Override
    public void onUpdate(UserEntity from, UserEntity to) {
        final DomainMetadata domainMetadata = DomainContextHolder.get(UserBinLog.class);
        final List<String> fields = domainMetadata.getFields();
        final List<Field> collect1 = Arrays.stream(UserEntity.class.getDeclaredFields())/*.map(f -> f.getName())*/
                .collect(Collectors.toList());
        List<Field> mappingFields = new ArrayList<>();
        for (String field : fields) {
            for (Field s : collect1) {
                if (field.equals(s)) {
                    mappingFields.add(s);
                }
            }
        }
        mappingFields.forEach(f -> {
            f.setAccessible(true);
            try {
                if (f.getName().contains("From")) {
                    // 变化前的值
                    final Object o = f.get(from);
                    domainMetadata.setFieldValue(f.getName() + "Form", o);
                }
                if (f.getName().contains("To")) {
                    // 变化后的值
                    final Object o1 = f.get(to);
                    domainMetadata.setFieldValue(f.getName() + "To", o1);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            logService.insert(domainMetadata);
        });
    }

    @Override
    public void onInsert(UserEntity data) {

    }

    @Override
    public void onDelete(UserEntity data) {

    }
}
