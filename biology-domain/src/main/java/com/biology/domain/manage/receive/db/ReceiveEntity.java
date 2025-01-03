package com.biology.domain.manage.receive.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_receive")
@ApiModel(value = "ReceiveEntity对象", description = "领用记录")
public class ReceiveEntity extends BaseEntity<ReceiveEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("receive_id")
    @TableId(value = "receive_id", type = IdType.AUTO)
    private Long receiveId;

    @ApiModelProperty("领用人Id")
    @TableField("receive_user_id")
    private Long receiveUserId;

    @ApiModelProperty("materials_id")
    @TableField("materials_id")
    private Long materialsId;

    // 领用数量
    @ApiModelProperty("receive_num")
    @TableField("receive_num")
    private double receiveNum;

    @ApiModelProperty("领用说明")
    @TableField("receive_explain")
    private String receiveExplain;

    @ApiModelProperty("out_id")
    @TableField("out_id")
    private Long outId;

    @Override
    public Serializable pkVal() {
        return this.receiveId;
    }

}
