package com.zzj.rule.engine.server.common.framework.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 带tenantId的base entity
 * @date 06/18/2021
 **/
@Data
public class BaseTenantEntity<T extends BaseTenantEntity> extends BaseEntity<T> {

    /**
     * 租户id
     */
    @TableField(fill = FieldFill.INSERT)
    private String tenantId;
}