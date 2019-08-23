package cc.nanjo.common.tieba;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.libsgh.tieba.api.TieBaApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sound.sampled.Line;
import java.util.Map;

/**
 * @author xw
 * @date 2019/8/23 14:52
 */
@Component
@Slf4j
public class TiebaSignExecute {

    @Scheduled(cron = "0 0 4,6,8 * * ? ")
    public void signingAllTieba() throws Exception {
        TieBaApi tieBaApi = TieBaApi.getInstance();
        Resource resource = new ClassPathResource("config.json");
        String configJson = IOUtils.toString(resource.getInputStream());
        JSONObject jsonObject = JSONObject.parseObject(configJson);
        JSONArray baiduAccounts = jsonObject.getJSONArray("baiduAccounts");
        for (Object baiduAccount : baiduAccounts) {
            JSONObject jsonAccounts = JSONObject.parseObject(baiduAccount.toString());
            log.info("当前百度账号：{}", jsonAccounts.getString("username"));
            log.info("百度贴吧签到开始----------------------------");
            Map<String, Object> stringObjectMap = tieBaApi.oneBtnToSign(jsonAccounts.getString("bduss"), jsonAccounts.getString("stoken"));
            log.info("百度贴吧签到：{}", JSONObject.toJSONString(stringObjectMap));
            log.info("百度知道签到开始----------------------------");
            String zhidaoRes = tieBaApi.zhiDaoSign(jsonAccounts.getString("bduss"));
            log.info("百度知道签到：{}", zhidaoRes);
            log.info("百度文库签到开始----------------------------");
            String wenkuRes = tieBaApi.wenKuSign(jsonAccounts.getString("bduss"));
            log.info("百度文库签到：{}", wenkuRes);
        }


    }

}
