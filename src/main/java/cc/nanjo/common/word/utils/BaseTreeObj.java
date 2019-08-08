package cc.nanjo.common.word.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  描述：基层树形结构实体类 --ID AND PARENTID AND CHILDSLIST
 *  作者：xfz
 *  时间：2016 年 7 月 2 日
 *  版本号：1.0
 */
@Data
public class BaseTreeObj<E, ID extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;
    private ID id;
    private ID parentId;
    private List<E> childsList = new ArrayList<E>();

}
