package com.biology.domain.manage.kongTiaoData.command;

import lombok.Data;

@Data
public class KongTiaoDataOpc {
    private String deviceSn;

    private Double zhiBanGongKuanYaLiSheDing;

    private Double zhiBanGongKuanFengLiangSheDing;

    private Integer fengFaWenDingZhuangTai;

    private Integer faWeiFanKuan;

    private Integer qiangZhiFaWeiSheDing;

    private Integer qiangZhiMoShiKaiGuan;

    private Integer pidKongZhiJiFenXiShu;

    private Integer podKongZhiBiLiXiShu;

    private Integer fengLiangFanKui;

    private Double fangJianShiJiYaLi;

    private Integer gongKuangMoShi;

    private Integer shuangGongKuangQieHuanShiJian;

    private Integer fengLiangSheDing;

    private Double yaLiSheDing;
}
