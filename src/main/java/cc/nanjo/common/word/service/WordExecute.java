package cc.nanjo.common.word.service;

import cc.nanjo.common.util.StringUtil;
import cc.nanjo.common.word.entity.ResponseEntity;
import cc.nanjo.common.word.entity.Word;
import cc.nanjo.common.word.utils.BaseTreeObj;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @Author: xw
 * 2019/8/7/007 22:56
 */
@Slf4j
@Service
public class WordExecute {

    private final static String finalWord = "为所欲为";
    private List<String> resultList = new ArrayList<>();
    private Map<Integer, String> resultMap = new TreeMap<>();
    private Set<String> wordSet = new TreeSet<>();

    private int count;

    @Autowired
    private WordService wordService;

    public ResponseEntity execute(String word) {

        resultList.clear();
        resultMap.clear();
        wordSet.clear();
        count = 1;

        Word wordData = wordService.getOne(new LambdaQueryWrapper<Word>().eq(Word::getWord, word));
        if (wordData == null) {
            return ResponseEntity.fail("填写的不是成语哦，不要捣乱");
        }

        String pinyinEnd = wordData.getPinyinEnd();
        if (pinyinEnd.equals("wei")) {
            return ResponseEntity.fail("这个成语已经可以直接接为所欲为了哦");
        }
        resultMap.put(count, wordData.getWord());
        // 先查有没有直接对应的1级关联
        List<Word> wei = wordService.list(new LambdaQueryWrapper<Word>().eq(Word::getPinyinStart, pinyinEnd).eq(Word::getPinyinEnd, "wei"));
        if (wei != null && wei.size() != 0) {
            Word word1 = wei.get(0);
            resultMap.put(++count, word1.getWord());
            resultMap.put(++count, finalWord);
        } else {
            // 若没有 一级一级往下查
            List<Word> list = wordService.list(new LambdaQueryWrapper<Word>().eq(Word::getPinyinStart, pinyinEnd));
            this.coveList(list);
        }
        log.info(JSONObject.toJSONString(resultMap));
        resultMap.forEach((k, s) -> {
            resultList.add(k + " - " + s);
        });
        return ResponseEntity.success(String.join("，", resultList));

    }

    private void coveList(List<Word> list) {
        List<Word> wei;
        count++;
        for (int i = 0; i < list.size(); i++) {
            Word word = list.get(i);
            if (wordSet.contains(word.getWord())) {
                continue;
            } else {
                wordSet.add(word.getWord());
            }
            log.info("正在查找...[{}]", word.getWord());
            resultMap.put(count, word.getWord());
            wei = wordService.list(new LambdaQueryWrapper<Word>().eq(Word::getPinyinStart, word.getPinyinEnd()).eq(Word::getPinyinEnd, "wei"));
            if (wei != null && wei.size() != 0) {
                Word word1 = wei.get(0);
                resultMap.put(++count, word1.getWord());
                resultMap.put(++count, finalWord);
                return;
            } else if (i == list.size() - 1) {
                // 若没有 一级一级往下查
                list = wordService.list(new LambdaQueryWrapper<Word>().eq(Word::getPinyinStart, word.getPinyinEnd()));
                this.coveList(list);
            }
        }
    }

    public boolean queryWord(List<Word> list) {
        for (Word wordOld : list) {
            // 先查有没有直接对应的1级关联
            List<Word> wei = wordService.list(new LambdaQueryWrapper<Word>().eq(Word::getPinyinStart, wordOld.getPinyinEnd()).eq(Word::getPinyinEnd, "wei"));
            if (wei != null && wei.size() != 0) {
                Word word1 = wei.get(0);
                resultMap.put(++count, word1.getWord());
                return true;
            } else {
                // 若没有 一级一级往下查
                list = wordService.list(new LambdaQueryWrapper<Word>().eq(Word::getPinyinStart, wordOld.getPinyinEnd()));
                this.queryWord(list);
            }
        }
        return false;
    }

}
