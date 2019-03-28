package cc.nanjo.db;

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
            // String db = "master";
            String db = "master";
            List<String> list = Arrays.asList("");
            byte[] zipByte = genComp.generatorCode(db, list);
            FileUtils.writeByteArrayToFile(new File("E:\\DbFiles\\" + System.currentTimeMillis() + ".zip"), zipByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
