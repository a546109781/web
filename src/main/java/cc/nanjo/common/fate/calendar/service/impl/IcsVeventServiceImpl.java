package cc.nanjo.common.fate.calendar.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.dynamic.datasource.annotation.DS;
import cc.nanjo.common.fate.calendar.entity.IcsVevent;
import cc.nanjo.common.fate.calendar.service.IcsVeventService;
import cc.nanjo.common.fate.calendar.mapper.IcsVeventMapper;

/**
 * Module: IcsVevent.java
 *
 * @date 2019-07-26
 */
@Service
@DS("master")
public class IcsVeventServiceImpl extends ServiceImpl<IcsVeventMapper, IcsVevent> implements IcsVeventService {

}
