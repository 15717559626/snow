package com.sun.snow.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author lish
 * @since 2019/10/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色权限编号
     */
    @TableId
    private String rolePermissionId;

    /**
     * 角色编号
     */
    private String roleId;

    /**
     * 权限编号
     */
    private String permissionId;

    /**
     * 角色权限状态
     */
    private String rolePermissionStatus;


}
