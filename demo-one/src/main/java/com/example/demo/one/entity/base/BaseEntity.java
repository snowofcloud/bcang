package com.example.demo.one.entity.base;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther xuxq
 * @date 2018/11/23 14:20
 */
@Data
public class BaseEntity extends Model<BaseEntity> {

    private Long id;
    /**
     * 创建人
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;
    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private Date createdTime;
    /**
     * 更新人
     */
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;
    /**
     * 更新时间
     */
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
