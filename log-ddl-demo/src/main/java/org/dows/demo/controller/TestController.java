package org.dows.demo.controller;

import org.dows.demo.eitity.UserEntity;
import org.dows.demo.log.UserEntityLog;
import org.dows.demo.mapper.UserEntityMapper;
import org.dows.demo.request.AddRequest;
import org.dows.log.api.annotation.AuditLog;
import org.dows.log.api.annotation.type.LogActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: lujl
 * @Date: 2023\1\9 0009 14:19
 */
@RestController
public class TestController {

    @Autowired
    private UserEntityMapper mapper;

    @AuditLog(type = LogActionType.ADD, tableSchemaClass = UserEntityLog.class)
    @PostMapping(value = "/testAuditLog")
    public ResponseEntity<Object> testAuditLog(@RequestBody AddRequest addRequest) {
        Map<String, String> result = new HashMap<>(1);
        mapper.insert(new UserEntity(11111, "dd"));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
