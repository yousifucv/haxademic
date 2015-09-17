package com.haxademic.app.haxmapper.dmxlights;

import com.haxademic.core.app.P;
import com.haxademic.core.draw.color.ColorHaxEasing;
import com.haxademic.core.hardware.dmx.DmxInterface;
import com.haxademic.core.math.MathUtil;

import processing.core.PApplet;
import processing.core.PGraphics;

public class RandomLightTiming {
	
	protected PApplet p;
	protected DmxInterface _dmx;
	protected ColorHaxEasing[] _colors;
	
	public RandomLightTiming(int numLights) {
		p = P.p;
		_dmx = new DmxInterface(numLights);
		_colors = new ColorHaxEasing[numLights];
		for (int i = 0; i < _colors.length; i++) {
			_colors[i] = new ColorHaxEasing("#000000", 5);
		}
	}
	
	public void updateDmxLightsOnBeat() {
		for (int i = 0; i < _colors.length; i++) {
			if(MathUtil.randBoolean(p) == true) {
				if(MathUtil.randBoolean(p) == true) {
					_colors[i].setCurrentColorInt( randomColor(0.7f) );
					_colors[i].setTargetColorInt(p.color(0));
				} else {
					_colors[i].setTargetColorInt( randomColor( p.random( 0.2f, 0.7f ) ) );
				}
			}
		}
	}
	
	public void update() {
		for (int i = 0; i < _colors.length; i++) {
			_colors[i].update();
			_dmx.setColorAtIndex(i, _colors[i].colorInt(dmxMultiplier()));
		}
		_dmx.updateColors();
	}

	protected int randomColor( float mult ) {
		float baseR = 180 + 55 * P.sin(p.frameCount/100);
		float baseG = 180 + 55 * P.sin(p.frameCount/120);
		float baseB = 180 + 55 * P.sin(p.frameCount/135);
		return p.color( 
				(baseR + p.random(-20, 20)) * mult,
				(baseG + p.random(-20, 20)) * mult,
				(baseB + p.random(-20, 20)) * mult
				);
	}
	
	protected float dmxMultiplier() {
		return 1;
//		return P.p.midi.midiCCPercent(0, 41) * 1.5f;
	}
	
	public void drawDebug(PGraphics pg) {
		int debugSize = 100;
		int x = pg.width - _colors.length * debugSize;
		for (int i = 0; i < _colors.length; i++) {
			p.fill(_colors[i].colorInt(dmxMultiplier()));
			p.rect(x + i * debugSize, 0, debugSize, debugSize);
		}
	}
}