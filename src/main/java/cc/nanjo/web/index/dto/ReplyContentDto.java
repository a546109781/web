package cc.nanjo.web.index.dto;

import lombok.Data;

/**
 * @Author: xw
 * 2019-4-2 10:51
 */
@Data
public class ReplyContentDto {

    private String replyId;
    private String content;
    private String author;
    private String createTime;
    private String updateTime;
    private String replyTo;


}
