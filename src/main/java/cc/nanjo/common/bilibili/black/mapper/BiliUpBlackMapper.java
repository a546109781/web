package cc.nanjo.common.bilibili.black.mapper;


import cc.nanjo.common.bilibili.black.entity.BiliUpBlack;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Module: BiliUpBlack.java
 *
 * @date 2020-03-04
 */
@Repository
public interface BiliUpBlackMapper extends BaseMapper<BiliUpBlack> {

    @Select("select black_name from bili_up_black where user = #{user}")
    List<String> getUpNames(@Param("user") String user);
}
