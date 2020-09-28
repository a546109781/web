package cc.nanjo.common.bilibili.black.service.impl;

import cc.nanjo.common.bilibili.black.entity.BiliUpBlack;
import cc.nanjo.common.bilibili.black.entity.Response;
import cc.nanjo.common.bilibili.black.mapper.BiliUpBlackMapper;
import cc.nanjo.common.bilibili.black.service.BiliUpBlackService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Module: BiliUpBlack.java
 *
 * @date 2020-03-04
 */
@Service
@DS("master")
public class BiliUpBlackServiceImpl extends ServiceImpl<BiliUpBlackMapper, BiliUpBlack> implements BiliUpBlackService {

    @Autowired
    private BiliUpBlackMapper biliUpBlackMapper;

    @Override
    public Response selectBlackListJson(int pageNum, int pageSize) {
        Page<BiliUpBlack> page = new Page<>(pageNum, pageSize);
        IPage<BiliUpBlack> biliUpBlackIPage = biliUpBlackMapper.selectPage(page, null);
        return new Response<>(biliUpBlackIPage, biliUpBlackIPage.getTotal());
    }

    @Override
    public List<String> getUpNames(String user) {
        return biliUpBlackMapper.getUpNames(user);
    }
}
