package org.dows.demo.entity.log;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
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
@Table(name = "user_bin_log")
@TableName("user_bin_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBinLog {

    @Id
    @Column(nullable = false, columnDefinition = "int(38) comment '主键id'")
    private Integer id;

    @Column(columnDefinition = "varchar(50) comment '用户信息1'")
    private String userNameForm;

    @Column(columnDefinition = "varchar(50) comment '用户信息2'")
    private String userNameTo;

    @Column(columnDefinition = "varchar(50) comment '用户信息3'")
    private String userAgeForm;

    @Column(columnDefinition = "varchar(50) comment '用户信息3'")
    private String userAgeTo;
//    @Column(columnDefinition = "varchar(50) comment '新加的字段'")
//    private String newColumn;

}
