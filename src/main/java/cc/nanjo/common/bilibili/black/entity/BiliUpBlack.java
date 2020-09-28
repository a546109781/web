package cc.nanjo.common.bilibili.black.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Module: BiliUpBlack.java
 *
 * @date 2020-03-04
 */
@Data
@TableName(value = "bili_up_black")
@AllArgsConstructor
@NoArgsConstructor
public class BiliUpBlack  {

    /**  */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**  */
    private String user;
    /**  */
    private String blackName;
}
