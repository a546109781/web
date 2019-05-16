package cc.nanjo.img;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xw
 * 2019-4-18 15:31
 */
public class ImgTest {


    @Test
    public void testImg() throws Exception {

        String str = "你看有人理你吗";
        String path = "src/main/resources/static/other/img/1.jpg";
        int strMaxHeight = 28, strMinHeight = 14;
        FileInputStream inputStream = new FileInputStream(new File(path));
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.fillRect(0, bufferedImage.getHeight() - strMaxHeight, bufferedImage.getWidth(), strMaxHeight);
        graphics.setColor(Color.WHITE);
        int fontSize = bufferedImage.getWidth() / getRealLength(str);
        fontSize = fontSize > strMaxHeight ? strMaxHeight : fontSize;
        fontSize = fontSize < strMinHeight ? strMinHeight : fontSize;
        graphics.setFont(new Font("Xhei Mono.Dongqing", Font.PLAIN, fontSize));
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        graphics.setColor(Color.black);
        if (fontSize == strMinHeight) {
            List<String> stringList = get2strByRealLength(str);
            int x = (bufferedImage.getWidth() - (fontSize * getRealLength(stringList.get(0)))) / 2;
            graphics.drawString(stringList.get(0), x, bufferedImage.getHeight() - 22);
            graphics.drawString(stringList.get(1), x, bufferedImage.getHeight() - 18 + fontSize);
        } else {
            int x = (bufferedImage.getWidth() - (fontSize * getRealLength(str))) / 2;
            graphics.drawString(str, x, bufferedImage.getHeight() - 10);
        }
        ImageIO.write(bufferedImage, "JPEG", new FileOutputStream("C:\\Users\\Xanthuim\\Desktop\\111.jpg"));

    }

    private int getRealLength(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        valueLength = valueLength % 2 != 0 ? valueLength + 1 : valueLength;
        return valueLength / 2;
    }

    private List<String> get2strByRealLength(String value) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> splitList = new ArrayList<>();
        int realLength = getRealLength(value) * 2;
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";

        for (int i = 0; i < value.length(); i++) {
            stringBuilder.append(value.charAt(i));
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
            if (valueLength == realLength / 2 || valueLength - 1 == realLength / 2) {
                splitList.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }
            if (i == value.length() - 1) {
                splitList.add(stringBuilder.toString());
            }
        }
        return splitList;
    }

    public static void main(String[] args) {
        ImgTest imgTest = new ImgTest();
    }

}
