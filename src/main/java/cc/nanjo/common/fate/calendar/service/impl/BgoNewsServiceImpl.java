package cc.nanjo.common.fate.calendar.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.dynamic.datasource.annotation.DS;
import cc.nanjo.common.fate.calendar.entity.BgoNews;
import cc.nanjo.common.fate.calendar.service.BgoNewsService;
import cc.nanjo.common.fate.calendar.mapper.BgoNewsMapper;

/**
 * Module: BgoNews.java
 *
 * @date 2019-07-26
 */
@Service
@DS("master")
public class BgoNewsServiceImpl extends ServiceImpl<BgoNewsMapper, BgoNews> implements BgoNewsService {

}
