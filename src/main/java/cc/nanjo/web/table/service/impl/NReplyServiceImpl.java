package cc.nanjo.web.table.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.dynamic.datasource.annotation.DS;
import cc.nanjo.web.table.entity.NReply;
import cc.nanjo.web.table.service.NReplyService;
import cc.nanjo.web.table.mapper.NReplyMapper;

/**
 * Module: NReply.java
 *
 * @date 2019-03-29
 */
@Service
@DS("master")
public class NReplyServiceImpl extends ServiceImpl<NReplyMapper, NReply> implements NReplyService {

}
