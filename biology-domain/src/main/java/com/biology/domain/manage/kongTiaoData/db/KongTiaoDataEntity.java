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

    @TableField(value = "pid_kong_zhi_bi_li_xi_shu")
    private Integer pidKongZhiBiLiXiShu;

    @TableField(value = "qiang_zhi_fa_wei_she_ding")
    private Integer qiangZhiFaWeiSheDing;

    @TableField(value = "qiang_zhi_mo_shi_kai_guan")
    private Integer qiangZhiMoShiKaiGuan;

    @TableField(value = "pid_kong_zhi_ji_fen_xi_shu")
    private Integer pidKongZhiJiFenXiShu;

    @TableField(value = "feng_liang_fan_kui")
    private Integer fengLiangFanKuan;

    @TableField(value = "fang_jian_shi_ji_ya_li")
    private Double fangJianShiJiYaLi;

    @TableField(value = "gong_kuang_mo_shi")
    private String gongKuangMoShi;

    @TableField(value = "shuang_gong_kuang_qie_huan_shi_jian")
    private Integer shuangGongKuangQieHuanShiJian;

    @TableField(value = "feng_liang_she_ding")
    private Integer fengLiangSheDing;

    @TableField(value = "ya_li_she_ding")
    private Double yaLiSheDing;

    @TableField(value = "device_type")
    private String deviceType;

    @TableField(value = "hui_feng_ji_shou_zi_dong")
    private String huiFengJiShouZiDong;

    @TableField(value = "hui_feng_ji_gu_zhang")
    private String huiFengJiGuZhang;

    @TableField(value = "hui_feng_ji_yun_xing")
    private String huiFengJiYunXing;

    @TableField(value = "hui_feng_mi_bi_kai_guan_kong_zhi")
    private String huiFengMiBiKaiGuanKongZhi;

    @TableField(value = "hui_feng_mi_bi_guan_dao_wei")
    private String huiFengMiBiGuanDaoWei;

    @TableField(value = "hui_feng_mi_bi_kai_dao_wei")
    private String huiFengMiBiKaiDaoWei;

    @TableField(value = "hui_feng_ji_qi_ting")
    private String huiFengJiQiTing;

    @TableField(value = "yuan_xin_feng_kou_zeng_jia_xin_feng_fa_kai_guan_kong_zhi")
    private String yuanXinFengKouZengJiaXinFengFaKaiGuanKongZhi;

    @TableField(value = "zeng_jia_xin_feng_kou_xin_feng_fa_kai_guan_kong_zhi")
    private String zengJiaXinFengKouXinFengFaKaiGuanKongZhi;

    @TableField(value = "yuan_xin_feng_kou_zeng_jia_xin_feng_fa_guan_dao_wei")
    private String yuanXinFengKouZengJiaXinFengFaGuanDaoWei;

    @TableField(value = "zeng_jia_xin_feng_kou_xin_feng_fa_guan_dao_wei")
    private String zengJiaXinFengKouXinFengFaGuanDaoWei;

    @TableField(value = "yuan_xin_feng_kou_zeng_jia_xin_feng_fa_kai_dao_wei")
    private String yuanXinFengKouZengJiaXinFengFaKaiDaoWei;

    @TableField(value = "zeng_jia_xin_feng_kou_xin_feng_fa_kai_dao_wei")
    private String zengJiaXinFengKouXinFengFaKaiDaoWei;

    @TableField(value = "mo_shi_qie_huan")
    private String moShiQieHuan;

    @TableField(value = "gong_kuang_qie_huan")
    private String gongKuangQieHuan;

    @TableField(value = "hui_feng_ji_pin_lv_fan_kuan")
    private String huiFengJiPinLvFanKuan;

    @TableField(value = "pai_feng_fa_zhi_guan_feng_fa_fan_kuan")
    private String paiFengFaZhiGuanFengFaFanKuan;

    @Override
    public Serializable pkVal() {
        return this.kongTiaoDataId;
    }
}
