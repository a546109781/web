package cc.nanjo.common.yzt.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
/**
 * Module: YztCulturalRelics.java
 *
 * @date 2020-04-27
 */
@Data
@TableName(value = "yzt_cultural_relics")
public class YztCulturalRelics  {

    /**  */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /** 名称 */
    private String name;
    /** 位置 */
    private String position;
    /** gps-经度 */
    private Double longitude;
    /** gps-纬度 */
    private Double latitude;
    /** gps-海拔高度 */
    private Double altitude;
    /** gps-测点说明 */
    private String measuringPointDescription;
    /** 文物类别 */
    private String category;
    /** 年代 */
    private String years;
    /** 统计年代 */
    private String period;
    /** 面积 */
    private Double area;
    /** 所有权 */
    private String ownership;
    /** 使用情况-使用单位 */
    private String useUnit;
    /** 使用情况-隶属 */
    private String subjection;
    /** 使用情况-用途 */
    private String useFor;
    /** 级别，1全国重点文物保护单位、2省级文物保护单位、3市县级文物保护单位、4尚未核定为		保护单位 */
    private String level;
    /** 单体文物-数量 */
    private Integer amount;
    /** 是否归属墓群 */
    private String isLocation;
    /** 单体文物-说明 */
    private String explanation;
    /** 简介 */
    private String introduction;
    /** 现状评估，1好、2较好、3一般、4较差、5差 */
    private String statusAssessment;
    /** 现状描述 */
    private String statusDescription;
    /** 损坏原因-人为因素 */
    private String damageHumanFactors;
    /** 损坏原因-自然因素 */
    private String damageNaturalFactors;
    /** 损坏原因描述 */
    private String damageDescription;
    /** 自然环境 */
    private String naturalEnvironment;
    /** 人文环境 */
    private String culturalEnvironment;
    /** 审核意见 */
    private String auditOpinion;
    /**  */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    /**  */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
    /** 备注 */
    private String remarks;
}
