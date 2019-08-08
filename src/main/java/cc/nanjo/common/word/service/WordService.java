package cc.nanjo.common.word.service;

import cc.nanjo.common.word.entity.Word;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Module: Word.java
 *
 * @date 2019-08-07
 */
public interface WordService extends IService<Word> {
    List<Word> getWordsByPyEnd(String pinyinEnd);
}
