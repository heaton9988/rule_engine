package com.zzj.rule.engine.server.common.framework.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


/**
 * @author : jianglei.d@bytedance.com
 * @date : 2019/11/29 9:27 下午
 * @description: 基础Entity
 */
@Data
public class BaseEntity<T extends BaseEntity> extends Model {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @TableField(fill = FieldFill.INSERT)
    protected Long id;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected String createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String updateTime;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    protected String creator;
    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String modifier;


    public static final String ID = "id";
    public static final String CREATOR = "creator";
    public static final String MODIFIER = "modifier";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";
    public static final String TENANT_ID = "tenant_id";

}