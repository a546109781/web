package cc.nanjo.web.table.entity;

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
 * Module: NContent.java
 *
 * @date 2019-03-29
 */
@Data
@TableName(value = "n_content")
public class NContent  {

    /** 内容ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 内容 */
    private String content;
    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
