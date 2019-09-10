package cc.nanjo.common.fate.calendar.mapper;

import cc.nanjo.common.fate.calendar.entity.IcsContent;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Module: IcsContent.java
 *
 * @date 2019-07-26
 */
@Mapper
public interface IcsContentMapper extends BaseMapper<IcsContent> {

    @Select("select * from ics_content order by create_time desc limit 1")
    IcsContent selectNewIcs();
}