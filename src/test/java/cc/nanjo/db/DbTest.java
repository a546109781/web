package cc.nanjo.db;

import cc.nanjo.common.db.GenComp;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: xw
 * 2019-3-28 11:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DbTest {

    @Resource
    GenComp genComp;

    @Test
    public void testGen() {
        try {
            String filePackage = "cc.nanjo.common.fate.calendar";
            String db = "master";
            List<String> list = Arrays.asList("bgo_news", "ics_vevent");
            byte[] zipByte = genComp.generatorCode(db, list, filePackage);
            FileUtils.writeByteArrayToFile(new File("D:\\DbFiles\\" + System.currentTimeMillis() + ".zip"), zipByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
