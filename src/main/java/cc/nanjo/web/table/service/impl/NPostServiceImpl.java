package cc.nanjo.web.table.service.impl;

import cc.nanjo.web.table.entity.NPost;
import cc.nanjo.web.table.mapper.NPostMapper;
import cc.nanjo.web.table.service.NPostService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Module: NPost.java
 *
 * @date 2019-03-29
 */
@Service
@DS("master")
public class NPostServiceImpl extends ServiceImpl<NPostMapper, NPost> implements NPostService {

}
