package org.dows.demo.service;

import org.dows.demo.entity.UserEntity;
import org.dows.framework.crud.mybatis.MybatisCrudService;

import java.util.List;

public interface UserService extends MybatisCrudService<UserEntity> {

    void insert(String str1, String str2);

    void delete(Integer id);

    List<UserEntity> queryAll(String param1);

}
