package com.biology.domain.manage.runTime.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddRunTimeCommand {

    private Long equipmentId;

    private Long environmentId;

    private Boolean isStop;

}
