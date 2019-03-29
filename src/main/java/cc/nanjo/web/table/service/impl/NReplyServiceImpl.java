package cc.nanjo.web.table.service.impl;

import cc.nanjo.web.table.entity.NReply;
import cc.nanjo.web.table.mapper.NReplyMapper;
import cc.nanjo.web.table.service.NReplyService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Module: NReply.java
 *
 * @date 2019-03-29
 */
@Service
@DS("master")
public class NReplyServiceImpl extends ServiceImpl<NReplyMapper, NReply> implements NReplyService {

}
