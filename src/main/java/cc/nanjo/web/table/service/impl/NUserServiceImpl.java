package cc.nanjo.web.table.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.dynamic.datasource.annotation.DS;
import cc.nanjo.web.table.entity.NUser;
import cc.nanjo.web.table.service.NUserService;
import cc.nanjo.web.table.mapper.NUserMapper;

/**
 * Module: NUser.java
 *
 * @date 2019-03-29
 */
@Service
@DS("master")
public class NUserServiceImpl extends ServiceImpl<NUserMapper, NUser> implements NUserService {

}
