package cc.nanjo.common.fate.table.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * Module: NServent.java
 *
 * @date 2019-04-01
 */
@Data
@TableName(value = "n_servent")
public class NServent  {

    /**  */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**  */
    private String sName;
    /**  */
    private Integer sex;
    /**  */
    private Integer starLevel;
    /**  */
    private Integer isNpc;
}
