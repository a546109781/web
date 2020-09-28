package cc.nanjo.web;

import cc.nanjo.common.fate.calendar.BgoNewToCalendarTask;
import cc.nanjo.common.fate.calendar.BgoNewsExecute;
import cc.nanjo.common.fate.calendar.service.BgoNewsService;
import cc.nanjo.common.fate.calendar.service.IcsVeventService;
import cc.nanjo.common.word.entity.Word;
import cc.nanjo.common.word.service.WordExecute;
import cc.nanjo.common.word.service.WordService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WebApplicationTests {

    @Autowired
    private BgoNewsExecute bgoNewsExecute;
    @Autowired
    private BgoNewsService bgoNewsService;
    @Autowired
    private IcsVeventService icsVeventService;
    @Autowired
    private BgoNewToCalendarTask bgoNewToCalendarTask;

    @Test
    public void testBgo() {
        bgoNewsService.remove(new LambdaQueryWrapper<>());
        icsVeventService.remove(new LambdaQueryWrapper<>());
        bgoNewsExecute.getBgoNewList();
    }

    @Test
    public void testBgo2() {

        bgoNewToCalendarTask.executeIcs();


    }

    @Autowired
    private WordService wordService;

    @Test
    public void testWord() throws Exception {
        String s = IOUtils.toString(new InputStreamReader(new FileInputStream(new File("C:\\Users\\admin\\Desktop\\idiom.json"))));

        JSONArray words = JSONObject.parseArray(s);

        List<Word> list = new ArrayList<>();
        Word wordObj;
        for (Object word : words) {
            String s1 = word.toString();
            JSONObject wordJson = JSONObject.parseObject(s1);

            String wordStr = wordJson.getString("word"); //成语
            String derivation = wordJson.getString("derivation"); // 出处
            String example = wordJson.getString("example"); // 示例
            String pinyin = wordJson.getString("pinyin"); // 拼音
            String abbreviation = wordJson.getString("abbreviation"); //首字母

            wordObj = new Word();
            wordObj.setAbbreviation(abbreviation);
            wordObj.setDerivation(derivation);
            wordObj.setExample(example);
            wordObj.setPinyin(pinyin);
            wordObj.setWord(wordStr);
            String[] split = pinyin.split(" ");
            wordObj.setPinyinStart(pinyinSplit(split[0]));
            wordObj.setPinyinEnd(pinyinSplit(split[split.length - 1]));
            list.add(wordObj);
        }

        wordService.saveOrUpdateBatch(list);

    }

    private Map<String, String> map;

    @Before
    public void before() {
        map = new HashMap<>();
        map.put("ā|á|ǎ|à", "a");
        map.put("ō|ó|ǒ|ò", "o");
        map.put("ē|é|ě|è", "e");
        map.put("ī|í|ǐ|ì", "i");
        map.put("ū|ú|ǔ|ù", "u");
        map.put("ǖ|ǘ|ǚ|ǜ", "v");


    }

    private String pinyinSplit(String pinyin) {
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            pinyin = pinyin.replaceAll(key, map.get(key));
        }
        return pinyin;
    }


}
