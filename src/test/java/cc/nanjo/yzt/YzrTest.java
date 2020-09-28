package cc.nanjo.yzt;

import cc.nanjo.common.yzt.entity.YztCulturalRelics;
import cc.nanjo.common.yzt.service.YztCulturalRelicsService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xw
 * @date 2020/4/26/026 15:32
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class YzrTest {


    private String dir = "D:\\dep\\temp\\output\\";

    @Autowired
    private YztCulturalRelicsService culturalRelicsService;

    @Test
    public void testFiles() {
        int count = 0;
        Map<String, Object> exception = new HashMap<>();
        Map<String, Object> idMap = new HashMap<>();
        File file = new File(dir);
        for (File listFile : file.listFiles()) {
            count++;
            if(count != 139){
                continue;
            }
            List<YztCulturalRelics> relicsList = new ArrayList<>();
            try {
                YztCulturalRelics yztCulturalRelics = new YztCulturalRelics();
                if (listFile.exists() && listFile.isFile()) {
                    XWPFDocument docx = new XWPFDocument(new FileInputStream(listFile));
                    // 文档文本内容
                    List<XWPFTable> tables = docx.getTables();
                    for (XWPFTable table : tables) {
                        List<XWPFTableRow> rows = table.getRows();
                        for (int j = 0; j < table.getRows().size(); j++) {
                            XWPFTableRow row = table.getRows().get(j);
                            for (int i = 0; i < row.getTableCells().size(); i++) {
                                XWPFTableCell xwpfTableCell = row.getTableCells().get(i);
                                String text = xwpfTableCell.getText().replaceAll(" ", "");
//                            log.info(text);
                                if (!StringUtils.isBlank(text)) {
                                    if ("名称".equals(text)) {
                                        yztCulturalRelics.setName(row.getTableCells().get(i + 1).getText().replaceAll(" ", ""));
                                    }
                                    if ("地址及位置".equals(text)) {
                                        yztCulturalRelics.setPosition(row.getTableCells().get(i + 1).getText().replaceAll(" ", ""));
                                    }
                                    if ("经度".equals(text)) {
                                        // 表格下一行
                                        XWPFTableRow xwpfTableRow = table.getRows().get(j + 1);
                                        yztCulturalRelics.setId(xwpfTableRow.getTableCells().get(0).getText().replaceAll(" ", "").replaceAll("-", ""));
                                        yztCulturalRelics.setLongitude(parse(xwpfTableRow.getTableCells().get(1).getText().replaceAll(" ", "")));
                                        yztCulturalRelics.setLatitude(parse(xwpfTableRow.getTableCells().get(2).getText().replaceAll(" ", "")));
                                        yztCulturalRelics.setAltitude(caseDouble(xwpfTableRow.getTableCells().get(3).getText().replaceAll(" ", "")));
                                        yztCulturalRelics.setMeasuringPointDescription(xwpfTableRow.getTableCells().get(4).getText().replaceAll(" ", ""));
                                    }
                                    if ("类别".equals(text) && !"数量".equals(row.getTableCells().get(i + 1).getText().replaceAll(" ", ""))) {
                                        String tableText = table.getText();
                                        String[] split = tableText.split("\n");
                                        for (int l = 0; l < split.length; l++) {
                                            String line = split[l];
                                            line = line.replaceAll(" ", "").replaceAll("\t", "");
                                            if (line.startsWith("类别")) {
                                                for (int x = l; x < (l + 6); x++) {
                                                    String type = split[x].replaceAll(" ", "").replaceAll("\t", "").replaceAll("类别", "");
                                                    if (type.startsWith("●其他")) {
                                                        yztCulturalRelics.setCategory("其他");
                                                        break;
                                                    }
                                                    if (type.startsWith("●")) {
                                                        type = type.substring(1, type.length() - 1);
                                                        yztCulturalRelics.setCategory(startToEnd(type, '●', '〇'));
                                                    }

                                                }
                                            }
                                        }
                                    }
                                    if ("统计年代".equals(text)) {
                                        XWPFTableRow xwpfTableRow = table.getRows().get(j - 1);
                                        List<XWPFTableCell> tableCells = xwpfTableRow.getTableCells();
                                        String s = tableCells.get(tableCells.size() - 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setYears(s);
                                        yztCulturalRelics.setPeriod(s);
                                    }
                                    if (text.startsWith("面积(")) {
                                        yztCulturalRelics.setArea(caseDouble(row.getTableCells().get(i + 1).getText().replaceAll(" ", "")));
                                    }
                                    if (text.startsWith("使用单位")) {
                                        yztCulturalRelics.setUseUnit(row.getTableCells().get(i + 1).getText().replaceAll(" ", ""));
                                    }
                                    if (text.startsWith("隶属")) {
                                        yztCulturalRelics.setSubjection(row.getTableCells().get(i + 1).getText().replaceAll(" ", ""));
                                    }
                                    if (text.startsWith("所有权")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setOwnership(startToEnd(s, '■', '□'));
                                    }
                                    if (text.startsWith("用途")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setUseFor(startToEnd(s, '■', '□'));
                                    }
                                    if (text.startsWith("级别")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setLevel(startToEnd(s, '●', '〇'));
                                    }
                                    if (text.startsWith("说明")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setExplanation(s);
                                    }
                                    if (text.startsWith("简介")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setIntroduction(s);
                                    }
                                    if (text.startsWith("现状评估")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setStatusAssessment(startToEnd(s, '●', '〇'));
                                    }
                                    if (text.startsWith("现状描述")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setStatusDescription(s);
                                    }
                                    if (text.startsWith("自然因素")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setDamageNaturalFactors(startToEnd(s, '■', '□'));
                                    }
                                    if (text.startsWith("人为因素")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setDamageHumanFactors(startToEnd(s, '■', '□'));
                                    }
                                    if (text.startsWith("损毁原因描述")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setDamageDescription(s);
                                    }
                                    if (text.startsWith("自然环境")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setNaturalEnvironment(s);
                                    }
                                    if (text.startsWith("人文环境")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setCulturalEnvironment(s);
                                    }
                                    if (text.startsWith("审核意见")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        yztCulturalRelics.setAuditOpinion(s);
                                    }
                                    if (text.startsWith("数量（")) {
                                        String s = row.getTableCells().get(i + 1).getText().replaceAll(" ", "");
                                        int num = Integer.parseInt(s);
                                        yztCulturalRelics.setAmount(num);
                                        yztCulturalRelics.setIsLocation("无");
                                        if (num > 1) {
                                            for (XWPFTable tableQ : tables) {
                                                for (int jq = 0; jq < tableQ.getRows().size(); jq++) {
                                                    XWPFTableRow rowQ = tableQ.getRows().get(jq);
                                                    for (int iq = 0; iq < rowQ.getTableCells().size(); iq++) {
                                                        XWPFTableCell xwpfTableCellQ = rowQ.getTableCells().get(iq);
                                                        String textQ = xwpfTableCellQ.getText().replaceAll(" ", "");
                                                        if (!StringUtils.isBlank(textQ)) {
                                                            if (textQ.startsWith("说明")) {
                                                                String sq = rowQ.getTableCells().get(iq + 1).getText().replaceAll(" ", "");
                                                                yztCulturalRelics.setExplanation(sq);
                                                            }
                                                            if (textQ.startsWith("简介")) {
                                                                String sq = rowQ.getTableCells().get(iq + 1).getText().replaceAll(" ", "");
                                                                yztCulturalRelics.setIntroduction(sq);
                                                            }
                                                            if (textQ.startsWith("现状评估")) {
                                                                String sq = rowQ.getTableCells().get(iq + 1).getText().replaceAll(" ", "");
                                                                yztCulturalRelics.setStatusAssessment(startToEnd(sq, '●', '〇'));
                                                            }
                                                            if (textQ.startsWith("现状描述")) {
                                                                String sq = rowQ.getTableCells().get(iq + 1).getText().replaceAll(" ", "");
                                                                yztCulturalRelics.setStatusDescription(sq);
                                                            }
                                                            if (textQ.startsWith("自然因素")) {
                                                                String sq = rowQ.getTableCells().get(iq + 1).getText().replaceAll(" ", "");
                                                                yztCulturalRelics.setDamageNaturalFactors(startToEnd(sq, '■', '□'));
                                                            }
                                                            if (textQ.startsWith("人为因素")) {
                                                                String sq = rowQ.getTableCells().get(iq + 1).getText().replaceAll(" ", "");
                                                                yztCulturalRelics.setDamageHumanFactors(startToEnd(sq, '■', '□'));
                                                            }
                                                            if (textQ.startsWith("损毁原因描述")) {
                                                                String sq = rowQ.getTableCells().get(iq + 1).getText().replaceAll(" ", "");
                                                                yztCulturalRelics.setDamageDescription(sq);
                                                            }
                                                            if (textQ.startsWith("自然环境")) {
                                                                String sq = rowQ.getTableCells().get(iq + 1).getText().replaceAll(" ", "");
                                                                yztCulturalRelics.setNaturalEnvironment(sq);
                                                            }
                                                            if (textQ.startsWith("人文环境")) {
                                                                String sq = rowQ.getTableCells().get(iq + 1).getText().replaceAll(" ", "");
                                                                yztCulturalRelics.setCulturalEnvironment(sq);
                                                            }
                                                            if (textQ.startsWith("审核意见")) {
                                                                String sq = rowQ.getTableCells().get(iq + 1).getText().replaceAll(" ", "");
                                                                yztCulturalRelics.setAuditOpinion(sq);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            yztCulturalRelics.setIsLocation(yztCulturalRelics.getName());
                                            List<XWPFTableRow> tableRows = tables.get(4).getRows();
                                            int rowNum = 2;
                                            for (rowNum = 2; rowNum < tableRows.size(); rowNum++) {
                                                List<XWPFTableCell> tableCells = tableRows.get(rowNum).getTableCells();
                                                yztCulturalRelics.setId(tableCells.get(0).getText().replaceAll(" ", "").replaceAll("-", ""));
                                                yztCulturalRelics.setLongitude(parse(tableCells.get(1).getText().replaceAll(" ", "")));
                                                yztCulturalRelics.setLatitude(parse(tableCells.get(2).getText().replaceAll(" ", "")));
                                                yztCulturalRelics.setAltitude(caseDouble(tableCells.get(3).getText().replaceAll(" ", "")));
                                                yztCulturalRelics.setMeasuringPointDescription(tableCells.get(4).getText().replaceAll(" ", ""));
                                                String rowName = tableCells.get(5).getText();
                                                if (!StringUtils.isBlank(rowName)) {
                                                    yztCulturalRelics.setName(rowName.replaceAll(" ", ""));
                                                }
//                                                log.info(yztCulturalRelics.toString());
                                                culturalRelicsService.save(yztCulturalRelics);
//                                                relicsList.add(yztCulturalRelics);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // 文档图片内容
                    /*List<XWPFPictureData> pictures = docx.getAllPictures();
                    for (XWPFPictureData picture : pictures) {
                        byte[] bytev = picture.getData();
                    }*/
                }
//                log.info(yztCulturalRelics.toString());
                idMap.put(listFile.getName(), yztCulturalRelics.getId());
                if (yztCulturalRelics.getAmount() == 1) {
                    culturalRelicsService.save(yztCulturalRelics);
//                    relicsList.add(yztCulturalRelics);
                }
/*                if (relicsList.size() == 100) {
                    culturalRelicsService.saveBatch(relicsList);
                    relicsList.clear();
                }*/
                log.info("============================================ [{}]", count);
            } catch (Exception e) {
                exception.put(listFile.getName(), e);
            }
        }
        log.info(exception.toString());
//        log.info(JSONObject.toJSONString(idMap));
    }

    DecimalFormat df = new DecimalFormat("#.000000000");

    private String startToEnd(String source, char begin, char end) {
        source = source.replaceAll(" ", "").replaceAll("\t", "");
        String[] split = source.split(begin + "");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (i != 0) {
                s = begin + s;
            }
            char[] chars = s.toCharArray();
            boolean b = false;
            for (char aChar : chars) {
                if (aChar == begin) {
                    b = true;
                }
                if (aChar == end) {
                    b = false;
                }
                if (b) {
                    stringBuilder.append(aChar);
                }
                if (!b && !StringUtils.isBlank(stringBuilder.toString())) {
                    break;
                }
            }
            stringBuilder.append(",");
        }
//        log.info(stringBuilder.toString());
        return stringBuilder.toString().substring(2, stringBuilder.toString().length() - 1).replaceAll(begin + "", "");
    }


    private double caseDouble(String s) {
        return Double.parseDouble(s.replaceAll("米", ""));
    }

    private double parse(String s) {
        Double a1;
        Double a2;
        Double a3;
        s = s.replaceAll("°", " ").replaceAll("'", " ").replaceAll("\"", "");
        String[] s1 = s.split(" ");
        a1 = Double.valueOf(s1[0]);
        a2 = Double.valueOf(s1[1]);
        a3 = Double.valueOf(s1[2]);
        a2 = a2 + (a3 / 60);
        double v = (a2 / 60) + a1;
        String format = df.format(v);
        String pre = format.substring(0, format.length() - 1);
        String sub = format.substring(format.length() - 1);
        double subDouble = Double.parseDouble(sub);
        if (subDouble % 2 != 0) {
            String substring = pre.substring(pre.length() - 1);
            int i = Integer.parseInt(substring) + 1;
            pre = pre.substring(0, format.length() - 1) + i;
        }
//        if(format.charAt(format.length()-1) % 2 != 0){
//            int i = Integer.parseInt(format.charAt(format.length() - 2) + "");
//            i++;
//        }
//        format = format.substring(0, format.length() - 2);
        return Double.parseDouble(pre);
    }

    @Test
    public void testParse() {
        log.info(parse("30°24'57.1") + "");
    }

}

