package cc.nanjo.common.fate.calendar.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
/**
 * Module: BgoNews.java
 *
 * @date 2019-07-26
 */
@Data
@TableName(value = "bgo_news")
public class BgoNews  {

    private String id;
    /** 0 其他 1 维护 2 活动 */
    private String type;
    /** 内容 */
    private String content;
    /** 标题 */
    private String title;
    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /** 修改时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;
}
