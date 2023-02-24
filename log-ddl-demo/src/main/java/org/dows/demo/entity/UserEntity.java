package org.dows.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author: lujl
 * @Date: 2023\1\6 0006 16:21
 */
@TableName("user_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

//    public static void main(String[] args) {
//        final TableName annotation = UserEntity.class.getAnnotation(TableName.class);
//    }

    @Id
    @Column(nullable = false, columnDefinition = "int(38) comment '主键id'")
    private Integer id;

    @Column(columnDefinition = "varchar(50) comment '用户名称'")
    private String userName;

//    @Column(columnDefinition = "varchar(50) comment '新加的字段'")
//    private String newColumn;

}
