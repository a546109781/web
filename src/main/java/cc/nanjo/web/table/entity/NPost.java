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
 * Module: NPost.java
 *
 * @date 2019-03-29
 */
@Data
@TableName(value = "n_post")
public class NPost  {

    /** 帖子ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 标题 */
    private String title;
    /** 内容ID */
    private Long contentId;
    /** 用户ID */
    private Long userId;
    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /** 点击 */
    private Integer pv;
}
