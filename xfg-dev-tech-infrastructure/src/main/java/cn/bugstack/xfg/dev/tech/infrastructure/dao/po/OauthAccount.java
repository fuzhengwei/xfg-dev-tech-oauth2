package cn.bugstack.xfg.dev.tech.infrastructure.dao.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OauthAccount implements Serializable {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 账号名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否可用
     */
    private Boolean enabled;

    /**
     * 账号未过期
     */
    private Boolean accountNonExpired;

    /**
     * 账号未锁定
     */
    private Boolean accountNonLocked;

    /**
     * 密码未过期
     */
    private Boolean credentialsNonExpired;

    /**
     * 账号未删除(逻辑删除)
     */
    private Boolean accountNonDeleted;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
