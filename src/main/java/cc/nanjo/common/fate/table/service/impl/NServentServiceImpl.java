package cc.nanjo.common.fate.table.service.impl;

import cc.nanjo.common.fate.table.entity.NServent;
import cc.nanjo.common.fate.table.mapper.NServentMapper;
import cc.nanjo.common.fate.table.service.NServentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * Module: NServent.java
 *
 * @date 2019-04-04
 */
@Service
@DS("master")
public class NServentServiceImpl extends ServiceImpl<NServentMapper, NServent> implements NServentService {

}
