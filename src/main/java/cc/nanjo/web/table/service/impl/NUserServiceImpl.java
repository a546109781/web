package cc.nanjo.web.table.service.impl;

import cc.nanjo.web.table.entity.NUser;
import cc.nanjo.web.table.mapper.NUserMapper;
import cc.nanjo.web.table.service.NUserService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Module: NUser.java
 *
 * @date 2019-03-29
 */
@Service
@DS("master")
public class NUserServiceImpl extends ServiceImpl<NUserMapper, NUser> implements NUserService {

}
