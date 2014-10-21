package com.lps.pssc.util;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageHelper {
	public static BufferedImage zoom(BufferedImage source, int targetW, int targetH) throws IOException {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		//等比例缩放
		/*if (sx > sy) {
			sx = sy;
			targetW = (int)(sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int)(sy * source.getHeight());
		}*/
		if (type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(targetW, targetH, type);
		}
		Graphics2D g = target.createGraphics();
//		g.drawImage(ImageIO.read(new File("")), 0, 0, null);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
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
}
