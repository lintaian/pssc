package com.lps.pssc.util;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageHelper {
	public static BufferedImage zoom(BufferedImage source, int max) throws IOException {
		int width = source.getWidth();
		int height = source.getHeight();
		if (Math.max(width, height) > max) {
			if (width > height) {
				height = max * height / width;
				width = max;
			} else {
				width = max * width / height;
				height = max;
			}
		} else {
			return source;
		}
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB );  
		Graphics g = image.getGraphics();
        g.drawImage(source, 0, 0, width, height, null); // 绘制缩小后的图  
        g.dispose();
		return image;
	}
	public static void zoom(String file, int max) throws Exception {
		zoom(file, file, max);
	}
	public static void zoom(String sourceFile, String targetFile, int max) throws Exception {
		BufferedImage image = zoom(ImageIO.read(new File(sourceFile)), max);
		File destFile = new File(targetFile);  
		FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流  
		// 可以正常实现bmp、png、gif转jpg  
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder  
				.getDefaultJPEGEncodeParam(image);  
		param.setQuality(0.7f, true);  
		encoder.setJPEGEncodeParam(param);  
		encoder.encode(image); // JPEG编码  
		out.close();  
	}

	public static BufferedImage cut(String src, int x, int y, int w, int h) throws IOException {
		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = iterator.next();
		InputStream in = new FileInputStream(src);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle rect = new Rectangle(x, y, w, h);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		return bi;
	}
	
	public static BufferedImage cutToSquare(String src) throws IOException {
		BufferedImage bi = ImageIO.read(new File(src));
		int width = bi.getWidth();
		int height = bi.getHeight();
		int x = 0,
			y = 0;
		if (width > height) {
			x = (width - height) / 2;
			width = height;
		} else {
			y = (height - width) / 2;
			height = width;
		}
		return cut(src, x, y, width, height);
	}
	public static void main(String[] args) throws IOException {
		BufferedImage image = zoom(ImageIO.read(new File("e:/1111.jpg")), 1024);
		File destFile = new File("e:/2222.jpg");  
        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流  
        // 可以正常实现bmp、png、gif转jpg  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder  
                .getDefaultJPEGEncodeParam(image);  
        param.setQuality(0.7f, true);  
        encoder.setJPEGEncodeParam(param);  
        encoder.encode(image); // JPEG编码  
        out.close();  
	}
}
