package io.github.weightrack;

import io.github.weightrack.utils.ImagePrinter;

import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.IOException;

import static io.github.weightrack.utils.ImageUtil.createImage;

public class Main {
    public static void main(String[] args) throws IOException {

        String[] data = {
                "2024年11月28日", "R2411280001", "陕A12345", "4-1水洗煤", "是", "是", "121.0", "22.0", "99.0", "17:12", "罗亚平"
        };
        BufferedImage image = createImage(data);// 打印图片
//        ImageIO.write(image, "png", new File("image.png"));

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


}
