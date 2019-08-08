package cc.nanjo.common.word.service.impl;

import cc.nanjo.common.word.entity.Word;
import cc.nanjo.common.word.mapper.WordMapper;
import cc.nanjo.common.word.service.WordService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Module: Word.java
 *
 * @date 2019-08-07
 */
@Service
@DS("master")
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements WordService {

    @Autowired
    private WordMapper wordMapper;

    @Override
    public List<Word> getWordsByPyEnd(String pinyinEnd) {
        return wordMapper.getWordsByPyEnd(pinyinEnd);
    }
}
