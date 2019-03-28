package cc.nanjo.db;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Component
public class GenComp {

    @Resource
    GenMapper genMapper;

    public byte[] generatorCode(String db, List<String> tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            Map<String, Object> table = genMapper.queryTable(tableName);
            List<Map<String, Object>> columns = genMapper.queryColumns(tableName);
            GenUtils.generatorCode(db, table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

}
