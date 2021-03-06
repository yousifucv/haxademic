package com.haxademic.core.render;

import java.io.File;

import com.haxademic.core.app.P;
import com.haxademic.core.file.FileUtil;
import com.haxademic.core.system.SystemUtil;
import com.haxademic.core.text.StringFormatter;

import processing.core.PGraphics;

public class ImageSequenceRenderer {

	protected String savePath;
	protected int framesRendered = 0;

	public ImageSequenceRenderer() {
	}
	
	public void startImageSequenceRender() {
		savePath = FileUtil.getHaxademicOutputPath() + SystemUtil.getTimestamp(P.p);
		FileUtil.createDir(savePath);
		P.println("== started rendering gif ==");
	}
		
	public void renderImageFrame(PGraphics pg) {
		if(framesRendered == -1) return;
		framesRendered++;
		P.println("== rendering image frame: "+framesRendered+" ==");
		pg.save(savePath + File.separator + StringFormatter.paddedNumberString(10, framesRendered) + ".png");
	}

	public void finish() {
		framesRendered = -1;
		P.println("== finished rendering image sequence ==");
	}
}
