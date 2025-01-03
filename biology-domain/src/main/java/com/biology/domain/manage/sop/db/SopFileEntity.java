package com.biology.domain.manage.sop.db;

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
@TableName("manage_sop_file")
@ApiModel(value = "SopEntity对象", description = "Sop信息表")
public class SopFileEntity extends Model<SopFileEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "sop_id", type = IdType.AUTO)
    private Long sopId;

    @TableField(value = "path")
    private String path;

    @Override
    public Long pkVal() {
        return this.sopId;
    }
}
