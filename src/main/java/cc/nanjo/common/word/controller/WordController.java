package cc.nanjo.common.word.controller;

import cc.nanjo.common.util.StringUtil;
import cc.nanjo.common.word.entity.ResponseEntity;
import cc.nanjo.common.word.entity.Word;
import cc.nanjo.common.word.service.WordExecute;
import cc.nanjo.common.word.service.WordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author xw
 * @date 2019/8/8 9:57
 */
//@Controller
@Slf4j
public class WordController {

    @Autowired
    private WordExecute wordExecute;


    @RequestMapping("wei-suo-yu-wei")
    public String wordMain(HttpServletRequest request, HttpServletResponse response) {
        return "word/index";
    }

    @RequestMapping("wei-suo-yu-wei/getWords")
    @ResponseBody
    public ResponseEntity getWords(HttpServletRequest request, String word) {
        if (StringUtil.isEmpty(word)) {
            return ResponseEntity.fail("请争取填写再提交");
        }
        return wordExecute.execute(word);
    }


}
