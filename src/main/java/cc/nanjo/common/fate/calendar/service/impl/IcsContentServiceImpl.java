package cc.nanjo.common.fate.calendar.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.dynamic.datasource.annotation.DS;
import cc.nanjo.common.fate.calendar.entity.IcsContent;
import cc.nanjo.common.fate.calendar.service.IcsContentService;
import cc.nanjo.common.fate.calendar.mapper.IcsContentMapper;

/**
 * Module: IcsContent.java
 *
 * @date 2019-07-26
 */
@Service
@DS("master")
public class IcsContentServiceImpl extends ServiceImpl<IcsContentMapper, IcsContent> implements IcsContentService {

}
