package io.github.weightrack;

import io.github.weightrack.utils.ImageUtil;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        String[] data = {
                "2024年11月28日", "R2411280001", "陕A12345", "4-1水洗煤", "是", "是", "121.0", "22.0", "99.0", "17:12", "罗亚平"
        };
        BufferedImage image = createImage(data);// 打印图片
        ImageIO.write(image, "png", new File("image.png"));

        try {
            // 设置打印纸张大小为80mm * 120mm
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            PageFormat pageFormat = printerJob.defaultPage();
            Paper paper = new Paper();

            // 80mm * 120mm转换为像素 (300 DPI)
            double width = 80 * 300 / 25.4;  // 80mm to pixels
            double height = 120 * 300 / 25.4; // 120mm to pixels
            paper.setSize(width, height);
            paper.setImageableArea(0, 0, width, height); // 设置页边距为0

            pageFormat.setPaper(paper);

            // 创建打印对象并设置打印内容
            ImagePrinter imagePrinter = new ImagePrinter(image); // 替换为你的图片路径
            printerJob.setPrintable(imagePrinter, pageFormat);

            printerJob.print();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NotNull
    public static BufferedImage createImage(String[] data) {
        int dpi = 300; // 打印机的DPI
        int widthMm = 80; // 图纸宽度，单位毫米
        int heightMm = 200; // 图纸高度，单位毫米

        // 将图纸尺寸转换为像素
        int widthPx = (int) (widthMm * dpi / 25.4) + 70;
        int heightPx = (int) (heightMm * dpi / 25.4) - 800;

        BufferedImage image = new BufferedImage(widthPx, heightPx, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 设置背景色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, widthPx, heightPx);

        // 设置支持中文的加粗字体
        Font font = new Font("Microsoft YaHei", Font.BOLD, 60);  // 微软雅黑字体，加粗
        g2d.setFont(font);

        // 设置文本颜色
        g2d.setColor(Color.BLACK);

        // 绘制说明文字：鸿聚一号煤场
        String header1 = "鸿聚一号煤场";
        FontMetrics fm = g2d.getFontMetrics();
        int stringWidth = fm.stringWidth(header1);
        int xHeader1 = (widthPx - stringWidth) / 2;  // 水平居中
        int yHeader1 = 80;  // 调整垂直位置

        g2d.drawString(header1, xHeader1, yHeader1);  // 绘制第一行标题

        // 绘制说明文字：过磅单
        String header2 = "过磅单";
        stringWidth = fm.stringWidth(header2);
        int xHeader2 = (widthPx - stringWidth) / 2;  // 水平居中
        int yHeader2 = yHeader1 + fm.getHeight();  // 第二行标题，向下偏移更多

        g2d.drawString(header2, xHeader2, yHeader2);  // 绘制第二行标题

        // 表格的标题和数据
        String[] columnHeaders = {"日期", "榜单编号", "车牌号", "煤种", "发货单位", "收货单位", "毛重", "皮重", "净重", "打印时间", "司磅员"};


        // 表格的行数和列数
        int cols = 2;  // 1列表头，1列数据
        int rows = columnHeaders.length;

        // 设置表格的起始位置（表格位置在说明文字下面）
        int x = 20;
        int y = yHeader2 + 45;  // 表格距离标题文字的距离

        // 表格单元格的宽度和高度
        int cellWidth = widthPx / cols - 20;
        int cellHeight = 121;  // 单元格高度增大

        font = new Font("Microsoft YaHei", Font.PLAIN, 40);
        g2d.setFont(font);

        // 设置表格的线条宽度
        g2d.setStroke(new BasicStroke(5)); // 设置线条粗细为10像素

        // 绘制表格的内容
        for (int i = 0; i < rows; i++) {
            // 第一列（表头）
            g2d.setColor(Color.WHITE);
            g2d.fillRect(x, y + i * cellHeight, cellWidth, cellHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y + i * cellHeight, cellWidth, cellHeight);

            // 计算文本的宽度和位置，确保文本左对齐
            stringWidth = fm.stringWidth(columnHeaders[i]);
            int stringX = x + 10;  // 水平左对齐，距离左边10像素
            int stringHeight = fm.getHeight();
            int stringY = y + i * cellHeight + (cellHeight + stringHeight) / 2 - fm.getDescent();  // 垂直居中

            g2d.drawString(columnHeaders[i], stringX + 40, stringY);  // 表头内容

            // 第二列（数据）
            g2d.setColor(Color.WHITE);
            g2d.fillRect(x + cellWidth, y + i * cellHeight, cellWidth, cellHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x + cellWidth, y + i * cellHeight, cellWidth, cellHeight);

            // 计算数据文本的宽度和位置，确保文本左对齐
            stringWidth = fm.stringWidth(data[i]);
            stringX = x + cellWidth + 10;  // 水平左对齐，距离左边10像素
            stringY = y + i * cellHeight + (cellHeight + stringHeight) / 2 - fm.getDescent();  // 垂直居中

            g2d.drawString(data[i], stringX + 40, stringY);  // 数据内容
        }

        g2d.dispose(); // 释放资源
        return image;
    }


}
