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

/**
 * @Author: xw
 * 2019-4-18 15:31
 */
public class ImgTest {


    @Test
    public void testImg() throws Exception {

        String str = "天天说自己穷的兄老师，拉菲说买就买，家住武汉一环";
        String path = "src/main/resources/static/other/img/1.jpg";
        int strMaxHeight = 30, strMinHeight = 15;
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
            String line1 = str.substring(0, (str.length()) / 2);
            String line2 = str.substring((str.length()) / 2);
            int x = (bufferedImage.getWidth() - (fontSize * getRealLength(line1))) / 2;
            graphics.drawString(line1, x, bufferedImage.getHeight() - 22);
            graphics.drawString(line2, x, bufferedImage.getHeight() - 18 + fontSize);
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
        return valueLength / 2;
    }

}
