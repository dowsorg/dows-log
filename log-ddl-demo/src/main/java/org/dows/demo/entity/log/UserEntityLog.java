package org.dows.demo.entity.log;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dows.demo.entity.UserEntity;
import org.dows.log.api.annotation.Binlog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: lujl
 * @Date: 2023\1\6 0006 16:21
 */
@Entity
@Table(name = "user_entity_log")
@Data
@NoArgsConstructor
@Binlog(hostName = "192.168.1.147", database = "log_ddl_demo", tableSchemaClass = UserEntity.class)
public class UserEntityLog extends Model<UserEntityLog> {

    @Id
    @Column(nullable = false, columnDefinition = "int(38) comment '主键id'")
    private Integer id;

    @Column(columnDefinition = "varchar(50) comment '用户信息1'")
    private String userInfo1;

    @Column(columnDefinition = "varchar(50) comment '用户信息2'")
    private String userInfo2;

    @Column(columnDefinition = "varchar(50) comment '用户信息3'")
    private String userInfo3;
//    @Column(columnDefinition = "varchar(50) comment '新加的字段'")
//    private String newColumn;

}
