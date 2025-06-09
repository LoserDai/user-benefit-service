package com.benefit.model.entity;

import com.benefit.model.enums.Status;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author Allen
 * @date 2025/6/6 14:19
 */
@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account", length = 50, nullable = false)
    private String account;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "password")
    private String password;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "phone", length = 20, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Status status;

    //是否为管理员 0 - 普通用户 1 - 管理员
    @Column(name = "user_role")
    private Integer userRole;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
}
