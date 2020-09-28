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
            String filePackage = "cc.nanjo.common.yzt";
            List<String> list = Arrays.asList("yzt_cultural_relics");
            byte[] zipByte = genComp.generatorCode("master", list, filePackage);
            File path = new File("D:\\dep\\temp\\" + System.currentTimeMillis() + ".zip");
            FileUtils.writeByteArrayToFile(path, zipByte);
            Runtime.getRuntime().exec("explorer /select," + path.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
