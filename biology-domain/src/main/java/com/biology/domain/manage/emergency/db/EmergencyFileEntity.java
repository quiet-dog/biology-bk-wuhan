package com.biology.domain.manage.emergency.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "EmergencyFileEntity对象", description = "应急库文件信息表")
@TableName("manage_emergency_file")
public class EmergencyFileEntity extends Model<EmergencyFileEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "emergency_id", type = IdType.AUTO)
    private Long emergencyId;

    @TableField(value = "path")
    private String path;

    @Override
    public Long pkVal() {
        return this.emergencyId;
    }
}
