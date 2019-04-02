package cc.nanjo.web.index.dto;

import lombok.Data;

/**
 * @Author: xw
 * 2019-4-1 15:18
 */
@Data
public class PostListDto {

    private Long id;
    private String title;
    private String time;
    private String content;
    private String img;
    private String pv;
    private String relayNum;
    private String author;

}
