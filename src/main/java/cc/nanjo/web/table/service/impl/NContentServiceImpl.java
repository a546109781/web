package cc.nanjo.web.table.service.impl;

import cc.nanjo.web.table.entity.NContent;
import cc.nanjo.web.table.mapper.NContentMapper;
import cc.nanjo.web.table.service.NContentService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Module: NContent.java
 *
 * @date 2019-03-29
 */
@Service
@DS("master")
public class NContentServiceImpl extends ServiceImpl<NContentMapper, NContent> implements NContentService {

}
