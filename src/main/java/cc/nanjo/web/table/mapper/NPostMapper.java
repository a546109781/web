package cc.nanjo.web.table.mapper;

import cc.nanjo.web.index.dto.PostContentDto;
import cc.nanjo.web.index.dto.PostListDto;
import cc.nanjo.web.table.entity.NPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Module: NPost.java
 *
 * @date 2019-03-29
 */
@Repository
public interface NPostMapper extends BaseMapper<NPost> {

    @Select("select \n" +
            "p.id AS id,\n" +
            "p.title AS title,\n" +
            "p.create_time AS time,\n" +
            "c.content as content,\n" +
            "p.pv as pv,\n" +
            "COUNT(r.id) as relayNum,\n" +
            "u.user_name as author\n" +
            "from \n" +
            "n_post p,\n" +
            "n_user u,\n" +
            "n_reply r,\n" +
            "n_content c\n" +
            "where\n" +
            "p.content_id = c.id\n" +
            "and p.user_id = u.id\n" +
            "and r.post_id = p.id\n" +
            "limit ${startNum}, ${num}")
    List<PostListDto> selectPostList(@Param("startNum") int startNum, @Param("num") int num);

    @Select("select \n" +
            "p.id AS postId, \n" +
            "p.title AS title, \n" +
            "c.content AS content, \n" +
            "u.user_name AS author, \n" +
            "p.create_time AS createTime, \n" +
            "p.update_time AS updateTime\n" +
            "from n_post p, n_content c, n_user u\n" +
            "where p.content_id = c.id and p.user_id = u.id\n" +
            "and p.id = #{postId}")
    PostContentDto selectPostContentByPostId(@Param("postId")Long postId);
}