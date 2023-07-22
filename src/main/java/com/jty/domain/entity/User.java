package com.jty.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2023-07-22 03:40:22
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User implements UserDetails {
    // 主键
    @TableId
    private Long id;
    // 用户名
    private String username;
    // 昵称
    private String nickName;
    // 密码
    private String password;
    // 用户类型：0代表普通用户，1代表管理员
    private String type;
    // 账号状态（0正常 1停用）
    private String status;
    // 邮箱
    private String email;
    // 手机号
    private String phonenumber;
    // 用户性别（0男，1女，2未知）
    private String sex;
    // 头像
    private String avatar;
    // 创建人的用户id
    private Long createBy;
    // 创建时间
    private Date createTime;
    // 更新人
    private Long updateBy;
    // 更新时间
    private Date updateTime;
    // 删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}