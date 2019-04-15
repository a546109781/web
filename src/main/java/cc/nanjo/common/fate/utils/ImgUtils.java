package cc.nanjo.common.fate.utils;

import cc.nanjo.common.fate.vo.Submits;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.AlphaComposite;
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

public class ImgUtils extends JPanel {
    private int width = 480;
    private int height;

    private int titleHeight = 40;

    private List<Submits> submits;
    private String savePath;

    public ImgUtils() {

    }

    public ImgUtils(List<Submits> submits) {
        this.submits = submits;
        this.height = (submits.size() * 100) + this.titleHeight;
        this.savePath = "";
    }

    public boolean getImg() throws Exception {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(new Color(169, 0, 20));
        graphics2D.fillRect(0, 0, width, titleHeight);
        graphics2D.setColor(new Color(128, 128, 128));
        graphics2D.fillRect(120, 0, 1, height);
        graphics2D.fillRect(300, 0, 1, height);
        graphics2D.fillRect(0, 40, width, 1);
        Font font = new Font("Xhei Mono.Dongqing", Font.PLAIN, 16);
        graphics2D.setFont(font);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        for (int i = 1; i <= submits.size(); i++) {
            Submits submits = this.submits.get(i - 1);
            graphics2D.fillRect(0, 40 + (i * 100), width, 1);
            FileInputStream inputStream = new FileInputStream(new File("src/main/resources/static/fate/img/avatar_" + String.format("%03d", submits.getId()) + ".jpg"));
            BufferedImage avatar = ImageIO.read(inputStream);
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.99f));
            graphics2D.drawImage(avatar, 25, (i - 1) * 100 + 50, 72, 78, null);
            graphics2D.drawString(submits.getPreInput(), 120, (i - 1) * 100 + 60);
            graphics2D.drawString(submits.getNowInput(), 300, (i - 1) * 100 + 60);
        }
        graphics2D.setFont(new Font("Xhei Mono.Dongqing", Font.PLAIN, 20));
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("英灵", 40, 28);
        graphics2D.drawString("初始印象", 180, 28);
        graphics2D.drawString("现在印象", 340, 28);
        //保存图片 JPEG表示保存格式
        return ImageIO.write(bufferedImage, "JPEG", new FileOutputStream("E:/a.jpg"));
    }

    public static void main(String[] args) {
        List<Submits> submits = new ArrayList<>();
        for (long i = 1; i <= 50; i++) {
            submits.add(new Submits(i, "但是它个大帅哥托尔斯泰各位", "但是它个大帅哥托尔斯泰各位"));
        }
        ImgUtils imgUtils = new ImgUtils(submits);
        try {
            imgUtils.getImg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



