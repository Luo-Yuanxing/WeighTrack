package io.github.weightrack;

import java.awt.*;
import java.awt.print.*;
import javax.swing.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImagePrinter implements Printable {
    private final BufferedImage image;

    public ImagePrinter(BufferedImage image) {
        this.image = image;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if (pageIndex >= 1) {
            return NO_SUCH_PAGE;
        }

        // 将图形上下文转换为Graphics2D，这样可以设置打印的属性
        Graphics2D g2d = (Graphics2D) graphics;

        // 设置打印方向
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        // 将图片缩放到纸张大小，按比例缩放
        double scaleX = pageFormat.getImageableWidth() / image.getWidth();
        double scaleY = pageFormat.getImageableHeight() / image.getHeight();
        double scale = Math.min(scaleX, scaleY);  // 保持比例，选择较小的缩放比例

        // 计算图片绘制的尺寸
        int newWidth = (int) (image.getWidth() * scale);
        int newHeight = (int) (image.getHeight() * scale);

        // 绘制图片
        g2d.drawImage(image, 0, 0, newWidth, newHeight, null);

        return PAGE_EXISTS;
    }
}
