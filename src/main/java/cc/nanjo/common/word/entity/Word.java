package cc.nanjo.common.word.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Module: Word.java
 *
 * @date 2019-08-07
 */
@Data
@TableName(value = "word")
public class Word implements Serializable {

    /**  */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /** 成语 */
    private String word;
    /** 出处 */
    private String derivation;
    /** 示例 */
    private String example;
    /** 拼音 */
    private String pinyin;
    /** 起始字拼音 */
    private String pinyinStart;
    /** 结尾字拼音 */
    private String pinyinEnd;
    /** 首字母 */
    private String abbreviation;
}
