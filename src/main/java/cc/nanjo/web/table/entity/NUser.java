package cc.nanjo.web.table.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Module: NUser.java
 *
 * @date 2019-03-29
 */
@Data
@TableName(value = "n_user")
public class NUser {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     *
     */
    private String userName;
    /**
     *
     */
    private String avatar;
}
