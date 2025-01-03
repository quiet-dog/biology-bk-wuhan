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
@ApiModel(value = "EmergencyKeywordEntity对象", description = "应急关键词表")
@TableName("manage_emergency_keyword")
public class EmergencyKeywordEntity extends Model<EmergencyKeywordEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "emergency_id", type = IdType.AUTO)
    private Long emergencyId;

    @TableField(value = "keyword_id")
    private Long keywordId;

    @Override
    public Long pkVal() {
        return this.keywordId;
    }
}
