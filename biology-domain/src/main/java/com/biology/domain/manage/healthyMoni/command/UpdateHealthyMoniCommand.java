package com.biology.domain.manage.healthyMoni.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateHealthyMoniCommand extends AddHealthyMoniCommand {
    private Long healthyMoniId;
}
