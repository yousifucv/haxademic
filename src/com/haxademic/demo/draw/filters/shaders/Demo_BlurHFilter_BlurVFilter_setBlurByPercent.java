package com.haxademic.demo.draw.filters.shaders;

import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.constants.AppSettings;
import com.haxademic.core.draw.context.DrawUtil;
import com.haxademic.core.draw.context.OpenGLUtil;
import com.haxademic.core.draw.filters.shaders.BlurHFilter;
import com.haxademic.core.draw.filters.shaders.BlurVFilter;
import com.haxademic.core.draw.image.ImageUtil;
import com.haxademic.core.file.DemoAssets;

import processing.core.PGraphics;
import processing.core.PImage;

public class Demo_BlurHFilter_BlurVFilter_setBlurByPercent
extends PAppletHax {
	public static void main(String args[]) { PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }

	public PImage img;
	public PGraphics pg;

	protected void overridePropsFile() {
		p.appConfig.setProperty( AppSettings.WIDTH, 800 );
		p.appConfig.setProperty( AppSettings.HEIGHT, 800 );
		p.appConfig.setProperty( AppSettings.SHOW_DEBUG, true );
	}


	public void setup() {
		super.setup();	
		p.smooth( OpenGLUtil.SMOOTH_HIGH );

		// load image and configure size
		img = DemoAssets.justin();

		// transform to blurred img
		pg = ImageUtil.imageToGraphics( DemoAssets.justin());
	}

	public void drawApp() {
		p.background(0);
		
		// redraw img to pg
		pg.beginDraw();
		pg.image(img, 0, 0);
		pg.endDraw();
		
		// apply blur
		float mousePercent = (float) p.mouseX / (float) p.width;
		p.debugView.setValue("Blur percent", mousePercent);
		BlurHFilter.instance(p).setBlurByPercent(mousePercent, img.width);
		BlurHFilter.instance(p).applyTo(pg);
		BlurVFilter.instance(p).setBlurByPercent(mousePercent, img.height);
		BlurVFilter.instance(p).applyTo(pg);
		
		// draw result to screen
		DrawUtil.setDrawCorner(p);
		p.image(pg, 0, 0);

	}

}
