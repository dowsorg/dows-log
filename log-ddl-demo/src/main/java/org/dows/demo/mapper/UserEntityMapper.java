package org.dows.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.demo.entity.UserEntity;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;

/**
 * @author: lujl
 * @Date: 2023\1\9 0009 17:02
 */
@Mapper
public interface UserEntityMapper extends MybatisCrudMapper<UserEntity> {

}
