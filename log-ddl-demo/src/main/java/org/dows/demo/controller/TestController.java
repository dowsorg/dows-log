package org.dows.demo.controller;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.dows.demo.request.AddRequest;
import org.dows.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lujl
 * @Date: 2023\1\9 0009 14:19
 */
@RestController
public class TestController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "/testAuditLog")
    public <T extends Model> ResponseEntity<Object> testAuditLog(@RequestBody AddRequest addRequest) {
        userService.insert(addRequest.getStr1(), addRequest.getStr2());
        return new ResponseEntity<>(Collections.EMPTY_MAP, HttpStatus.OK);
    }

    @PostMapping(value = "/testAuditLogDelete")
    public ResponseEntity<Object> testAuditLogDelete(@RequestBody AddRequest addRequest) {
        Map<String, String> result = new HashMap<>(1);
        userService.delete(11111);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/testAuditLogSelect")
    public ResponseEntity<Object> testAuditLogSelect(@RequestBody AddRequest addRequest) {
        Map<String, String> result = new HashMap<>(1);
        userService.delete(111);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
