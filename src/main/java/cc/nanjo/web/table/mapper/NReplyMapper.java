package cc.nanjo.web.table.mapper;

import cc.nanjo.web.index.dto.ReplyContentDto;
import cc.nanjo.web.table.entity.NReply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Module: NReply.java
 *
 * @date 2019-03-29
 */
@Repository
public interface NReplyMapper extends BaseMapper<NReply> {

    @Select("select\n" +
            "r.id AS replyId,\n" +
            "c.content AS content,\n" +
            "u.user_name AS author,\n" +
            "r.create_time AS createTime,\n" +
            "r.update_time AS updateTime,\n" +
            "r.reply_to AS replyTo\n" +
            "from\n" +
            "n_reply r,\n" +
            "n_user u,\n" +
            "n_content c\n" +
            "where r.user_id = u.id and r.content_id = c.id\n" +
            "and r.post_id = #{postId}")
    List<ReplyContentDto> selectReplyListByPostId(@Param("postId") Long postId);
}