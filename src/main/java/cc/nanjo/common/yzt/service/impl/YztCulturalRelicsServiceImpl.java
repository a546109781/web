package cc.nanjo.common.yzt.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.dynamic.datasource.annotation.DS;
import cc.nanjo.common.yzt.entity.YztCulturalRelics;
import cc.nanjo.common.yzt.service.YztCulturalRelicsService;
import cc.nanjo.common.yzt.mapper.YztCulturalRelicsMapper;

/**
 * Module: YztCulturalRelics.java
 *
 * @date 2020-04-27
 */
@Service
@DS("master")
public class YztCulturalRelicsServiceImpl extends ServiceImpl<YztCulturalRelicsMapper, YztCulturalRelics> implements YztCulturalRelicsService {

}
