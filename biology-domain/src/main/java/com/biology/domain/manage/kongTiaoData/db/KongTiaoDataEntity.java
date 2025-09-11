package com.biology.domain.manage.kongTiaoData.db;

import java.io.Serializable;

import org.intellij.lang.annotations.JdkConstants.TabLayoutPolicy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "manage_kong_tiao_data", autoResultMap = true)
@ApiModel(value = "KongTiaoDataEntity对象", description = "空调数据表")
public class KongTiaoDataEntity extends BaseEntity<KongTiaoDataEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "kong_tiao_data_id", type = IdType.AUTO)
    private Long kongTiaoDataId;

    @TableField(value = "device_sn")
    private String deviceSn;

    @TableField(value = "zhi_ban_gong_kuan_ya_li_she_ding")
    private Double zhiBanGongKuanYaLiSheDing;

    @TableField(value = "zhi_ban_gong_kuan_feng_liang_she_ding")
    private Double zhiBanGongKuanFengLiangSheDing;

    @TableField(value = "feng_fa_wen_ding_zhuang_tai")
    private Integer fengFaWenDingZhuangTai;

    @TableField(value = "fa_wei_fan_kuan")
    private Integer faWeiFanKuan;

    @TableField(value = "qiang_zhi_fa_wei_she_ding")
    private Integer qiangZhiFaWeiSheDing;

    @TableField(value = "qiang_zhi_mo_shi_kai_guan")
    private Integer qiangZhiMoShiKaiGuan;

    @TableField(value = "pid_kong_zhi_ji_fen_xi_shu")
    private Integer pidKongZhiJiFenXiShu;

    @TableField(value = "pod_kong_zhi_bi_li_xi_shu")
    private Integer podKongZhiBiLiXiShu;

    @TableField(value = "feng_liang_fan_kui")
    private Integer fengLiangFanKui;

    @TableField(value = "fang_jian_shi_ji_ya_li")
    private Double fangJianShiJiYaLi;

    @TableField(value = "gong_kuang_mo_shi")
    private Integer gongKuangMoShi;

    @TableField(value = "shuang_gong_kuang_qie_huan_shi_jian")
    private Integer shuangGongKuangQieHuanShiJian;

    @TableField(value = "feng_liang_she_ding")
    private Integer fengLiangSheDing;

    @TableField(value = "ya_li_she_ding")
    private Double yaLiSheDing;

    @Override
    public Serializable pkVal() {
        return this.kongTiaoDataId;
    }
}
