package com.haxademic.demo.draw.shapes;

import com.haxademic.core.app.P;
import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.constants.AppSettings;
import com.haxademic.core.draw.context.DrawUtil;
import com.haxademic.core.draw.image.ImageUtil;
import com.haxademic.core.draw.shapes.Icosahedron;
import com.haxademic.core.draw.shapes.PShapeSolid;
import com.haxademic.core.draw.shapes.PShapeUtil;
import com.haxademic.core.file.DemoAssets;
import com.haxademic.core.file.FileUtil;
import com.haxademic.core.math.MathUtil;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;

public class Demo_Icosahedron_Basic_REFACTOR
extends PAppletHax {
	public static void main(String args[]) { PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }

	PImage img;
	PShape icosWire;
	PShapeSolid icosa;


	protected void overridePropsFile() {
		p.appConfig.setProperty( AppSettings.WIDTH, 640 );
		p.appConfig.setProperty( AppSettings.HEIGHT, 640 );
	}

	public void setup() {
		super.setup();
		img = DemoAssets.textureJupiter();
		icosa = newSolidIcos(p.width * 0.3f, img);
		icosWire = Icosahedron.createIcosahedron(p.g, 5, img);
		PShapeUtil.scaleShapeToExtent(icosWire, p.width * 0.3f);
	}
	
	protected PShapeSolid newSolidIcos(float size, PImage texture) {
		PShape group = createShape(GROUP);
		PShape icos = Icosahedron.createIcosahedron(p.g, 5, texture);
		PShapeUtil.scaleShapeToExtent(icos, size);
		group.addChild(icos);
		return new PShapeSolid(group);
	}

	public void drawApp() {
		background(0);
		DrawUtil.setDrawCorner(p);
		
		// test mesh detail
//		icosa.shape().disableStyle();
//		icosWire.disableStyle();
		p.stroke(0,255,0);
		p.fill(0);
		
		// draw from center
		p.pushMatrix();
		p.translate(p.width/2, p.height/2);
		p.rotateY(P.map(p.mouseX, 0, p.width, 0, P.TWO_PI));
		p.shape(icosa.shape());
//		p.shape(icosWire);
		p.popMatrix();
	}

}

