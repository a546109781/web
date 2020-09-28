package cc.nanjo.common.bilibili.black.service;

import cc.nanjo.common.bilibili.black.entity.BiliUpBlack;
import cc.nanjo.common.bilibili.black.entity.Response;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Module: BiliUpBlack.java
 *
 * @date 2020-03-04
 */
public interface BiliUpBlackService extends IService<BiliUpBlack> {

    Response selectBlackListJson(int pageNum, int pageSize);

    List<String> getUpNames(String id);
}
