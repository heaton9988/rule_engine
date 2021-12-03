/**
 * *****************************************************
 * Copyright (C) 2021 bytedance.com. All Rights Reserved
 * This file is part of bytedance EA project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 **/
package com.zzj.rule.server.common.framework.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 带tenantId的base entity
 * @author zhaoanka<zhaoanka @ bytedance.com>
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