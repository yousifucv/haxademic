package com.haxademic.app.haxmapper;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;

import com.haxademic.app.haxmapper.overlays.MeshLines;
import com.haxademic.app.haxmapper.polygons.IMappedPolygon;
import com.haxademic.app.haxmapper.textures.BaseTexture;
import com.haxademic.core.app.P;
import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.math.MathUtil;

public class MappingGroup {

	protected PApplet p;
	protected ArrayList<IMappedPolygon> _mappedPolygons;
	protected ArrayList<BaseTexture> _curTextures;
	protected MeshLines _meshLines;
	protected int _color;

	public MappingGroup( PAppletHax p, PGraphics overlayPG ) {
		this.p = p;
		_curTextures = new ArrayList<BaseTexture>();
		_mappedPolygons = new ArrayList<IMappedPolygon>();
		_meshLines = new MeshLines( overlayPG );
		_color = P.p.color(255);
	}

	public void addPolygon( IMappedPolygon polygon ) {
		_mappedPolygons.add(polygon);
	}

	public void addMeshSegment( float x1, float y1, float x2, float y2 ) {
		_meshLines.addSegment( x1, y1, x2, y2 );
	}

	public void pushTexture( BaseTexture texture ) {
		if( _curTextures.indexOf( texture ) == -1 ) {
			_curTextures.add(texture);
		}
	}

	public BaseTexture shiftTexture() {
		if( _curTextures.size() > 0 ) {
			return _curTextures.remove(0);
		} else {
			return null;
		}
	}

	public ArrayList<IMappedPolygon> polygons() {
		return _mappedPolygons;
	}

	public ArrayList<BaseTexture> textures() {
		return _curTextures;
	}

	public void randomizeNextPolygon() {
		if( _curTextures.size() == 0 ) return;
		//			for(int i=0; i < _mappedPolygons.size(); i++ ) {
		IMappedPolygon triangle = _mappedPolygons.get( MathUtil.randRange( 0, _mappedPolygons.size() - 1 ) );
		int randTexture = MathUtil.randRange( 0, _curTextures.size() - 1 );
		triangle.setTexture(_curTextures.get(randTexture).texture());
		triangle.setTextureStyle( MathUtil.randBoolean(p) );
		triangle.rotateTexture();
		//			}		
	}
	
	public void setAllPolygonsToTexture( int textureIndex ) {
		for(int j=0; j < _mappedPolygons.size(); j++ ) {
			IMappedPolygon polygon = _mappedPolygons.get(j);
			polygon.setTexture( _curTextures.get(textureIndex).texture() );
		}
	}

	public void draw() {
		for(int j=0; j < _mappedPolygons.size(); j++ ) {
			IMappedPolygon triangle = _mappedPolygons.get(j);
			triangle.draw(p.g);
		}
	}

	public void drawOverlay() {
		_meshLines.update();
	}

	// update things for the group! ------------------------------------------
	public void newMode() {
		// make some decisions on how to make the group awesome
		// sometimes multiple groups should do the same larger things...

		for( int i=0; i < _curTextures.size(); i++ ) {
			_curTextures.get(i).newMode();
		}
	}

	public void newColor() {
		for( int i=0; i < _curTextures.size(); i++ ) {
			_curTextures.get(i).setColor( p.color(p.random(200,255), p.random(200,255), p.random(200,255), 255f ) );
		}
		_meshLines.setColor( p.color(p.random(200,255), p.random(200,255), p.random(200,255), 255f ) );
	}

	public void newLineMode() {
		for( int i=0; i < _curTextures.size(); i++ ) {
			_curTextures.get(i).newLineMode();
		}
		_meshLines.updateLineMode();
	}

	public void newRotation() {
		for( int i=0; i < _curTextures.size(); i++ ) {
			_curTextures.get(i).newRotation();
		}
	}

}