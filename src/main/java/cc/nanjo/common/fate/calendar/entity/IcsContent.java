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
 * Module: IcsContent.java
 *
 * @date 2019-07-26
 */
@Data
@TableName(value = "ics_content")
public class IcsContent  {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String content;
    @TableField(fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
