package cc.nanjo.common.word.mapper;


import cc.nanjo.common.word.entity.Word;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Module: Word.java
 *
 * @date 2019-08-07
 */
@Repository
public interface WordMapper extends BaseMapper<Word> {


    @Select("")
    List<Word> getWordsByPyEnd(@Param("pyEnd") String pinyinEnd);
}