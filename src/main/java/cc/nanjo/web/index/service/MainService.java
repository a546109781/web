package cc.nanjo.web.index.service;

import cc.nanjo.web.index.dto.PostContentDto;
import cc.nanjo.web.index.dto.PostListDto;
import cc.nanjo.web.index.dto.ReplyContentDto;
import cc.nanjo.web.table.mapper.NPostMapper;
import cc.nanjo.web.table.mapper.NReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xw
 * 2019-4-1 15:17
 */
@Service
public class MainService {

    @Autowired
    private NPostMapper nPostMapper;
    @Autowired
    private NReplyMapper nReplyMapper;


    public List<PostListDto> getPostList(int page, int num) {
        int startNum = page == 1 ? 0 : num * (page - 1);
        return nPostMapper.selectPostList(startNum, num);
    }

    public PostContentDto getPostContentByPostId(Long postId) {
        PostContentDto postContentDto = nPostMapper.selectPostContentByPostId(postId);
        List<ReplyContentDto> replyContentDtos = nReplyMapper.selectReplyListByPostId(postId);
        postContentDto.setReplyList(replyContentDtos);
        return postContentDto;
    }
}
