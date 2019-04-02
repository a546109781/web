package cc.nanjo.web.index.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: xw
 * 2019-4-2 10:47
 */
@Data
public class PostContentDto {

    private String postId;
    private String title;
    private String content;
    private String author;
    private String createTime;
    private String updateTime;
    private List<ReplyContentDto> replyList;


}
