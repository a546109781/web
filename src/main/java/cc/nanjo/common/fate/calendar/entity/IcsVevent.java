package cc.nanjo.common.fate.calendar.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Module: IcsVevent.java
 *
 * @date 2019-07-26
 */
@Data
@TableName(value = "ics_vevent")
public class IcsVevent {

    private String uid;
    private String type;
    private String bid;
    private String startTime;
    private String endTime;
    private String modifiTime;
    private String location;
    private String sequence;
    private String summary;
    private String url;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
}
