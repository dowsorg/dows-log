package org.dows.demo.entity.log;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 * @date 2023/2/23 16:24
 */
@Entity
@Table(name = "user_entity_audit_log")
@TableName("user_entity_audit_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuditLog {

    @Id
    @Column(nullable = false, columnDefinition = "int(38) comment '主键id'")
    private Long id;
    private String method_params;
    private String method_descr;
    private String method_name;
    private String ip;
    private String account_name;
    private String browser;
    private String user_name;
    private String device_id;

}
