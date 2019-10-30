package com.sun.snow.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author lish
 * @since 2019/10/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户角色编号
     */
    @TableId
    private String userRoleId;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 角色编号
     */
    private String roleId;

    /**
     * 用户角色状态，0正常，-1删除
     */
    private String userRoleStatus;


}
