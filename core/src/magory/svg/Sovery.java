package magory.svg;

/*
 * Sovery - SVG loader for level & interface design  
 * version 3.0
 * author: Tomasz Kucza
 * license: CC0 - just copy it and do whatever you want
 * 
 * Tips, bugs and stuff:
 * 1) make sure your SVG has scale set to 1 (default), otherwise it will be scaled
 * 2) override this class and write your own code for the newImage/newPath/newRect/newText
 * 3) width/height can be below zero - it means the image has to be flipped when drawing 
 * 4) the library depends on libGDX but should be easy to convert to another library
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public abstract class Sovery
{
	public String getLayerName(Element el)
	{
		if(el==null)
			return "";
		Element parent = el.getParent();
		if(parent==null)
			return "";
		String s = parent.getAttribute("inkscape:label", "");
		if(s==null)
			return getLayerName(parent);
		return s;
	}
	
	public String getTitle(Element el)
	{
		if(el==null)
			return "";
		Element tit = getChild(el,"title");
		if(tit!=null)
			return tit.getText();
		return "";
	}
	
	public String getParentTitle(Element el)
	{
		if(el==null)
			return "";
		if(el.getParent()==null)
			return "";
		Element tit = getChild(el.getParent(),"title");
		if(tit!=null)
			return tit.getText();
		return "";
	}
	
	public void load(String name)
	{
		load(Gdx.files.internal(name));
	}
	
	public void loadFromString(Element lev)
	{
		loadElement(lev, null);	
		onFinish();
	}
	
	public void load(FileHandle file)
	{
		XmlReader xr = new XmlReader();
		Element lev = new Element("a", null);		
		try
		{
			lev = xr.parse(file);
		}
		catch(Exception e)
		{
			Gdx.app.log("Solvery", "Error loading "+file);
		}

		loadElement(lev, null);	
		onFinish();
	}
	
	// override if you want
	public void onFinish()
	{
	}
	
	// statics
	
	public String getImageName(String trans)
	{
		// example: /media/amon/praca/android/fluffy/topack-game/tile-grass.png
		String name = "";
		try
		{			
			int en = trans.indexOf(".png");
			if(en<0)
				en = trans.indexOf(".jpg");
			int start = trans.lastIndexOf('/');
			int st2 = trans.lastIndexOf('\\'); // for SVGs saved on Windows - not tested, might not work!
			if(st2>start)
				start = st2;
			name = trans.substring(start+1, en);
				
		}
		catch(Exception e)
		{
			Gdx.app.log("Solvery", "Error getting filename: "+trans);
		}
		return name;
	}
	
	public static boolean isDigitOrSign(char a)
	{
		return Character.isDigit(a)||a=='-';
	}
	
	public static Array<Vector2> parsePathToArray(String d, float x, float y, float screenheight)
	{		
		Array<Vector2> path = new Array<Vector2>();
		if(!d.equals(""))
		{
			String commands[] = d.split("\\ ");
			if(commands.length>0)
			{
				String lastCommand = "";
			
				// only m,M,l,L,z,Z commands are supported
				for(int pos=0; pos<commands.length; pos++)
				{
					if(commands[pos].equals("m"))
						lastCommand = "m";
					else if(commands[pos].equals("M"))
						lastCommand = "M";
					else if(commands[pos].equals("L"))
						lastCommand = "L";
					else if(commands[pos].equals("l"))
						lastCommand = "l";
					else if(commands[pos].equals("Z")||commands[pos].equals("z"))
					{
						// close path
						if(path.size>0)
							path.add(new Vector2(path.get(1)));
					}
					else if(commands[pos].length()>0&&
							isDigitOrSign(commands[pos].charAt(0)))// assume coordinates
					{
						String coords[] = commands[pos].split(",");
						if(coords.length>1)
						{
							Vector2 c = new Vector2(getFloat(coords[0]), getFloat(coords[1]));
							if(path.size>0&&lastCommand.equals("m")||lastCommand.equals("l")) // relative coords
							{
								c.x=path.get(path.size-1).x+c.x;
								c.y=path.get(path.size-1).y+c.y; 
							}
							path.add(c);
						}
					}
				}
				for(int i=0; i<path.size; i++)
				{
					path.get(i).x+=x;
					path.get(i).y+=y;
					path.get(i).y = screenheight-path.get(i).y;
				}
			}
		}
		return path;
	}
	
	public boolean isRotation(String trans)
	{
		return trans.contains("rotate(");
	}
	
	public boolean isScale(String trans)
	{
		return trans.contains("scale(");
	}
	
	public boolean isMatrix(String trans)
	{
		return trans.contains("matrix(");
	}	
	
	public boolean isTranslate(String trans)
	{
		return trans.contains("translate(");
	}	
	
	public float[] getScaleFloats(String t)
	{
		return getTwoFloats(t, "scale(");
	}
	
	public float getRotationFloat(String t)
	{
		return getOneFloat(t, "rotate(");
	}
	
	public float[] getRotationFloats(String t)
	{
		return getThreeFloats(t, "rotate(");
	}
	
	public float[] getTranslateFloats(String t)
	{
		return getTwoFloats(t, "translate(");
	}
	
	public float[] getMatrixFloats(String t)
	{
		return getSixFloats(t,"matrix(");
	}
	
	public boolean isRect(String elname)
	{
		return elname.equals("rect");
	}
	public boolean isImage(String elname)
	{
		return elname.equals("image");
	}
	public boolean isText(String elname)
	{
		return elname.equals("text");
	}
	public boolean isStyle(String s, String style)
	{
		if(s.startsWith(style+":"))
			return true;
		return false;
	}
	
	public Element getChild(Element el, String name)
	{
		return el.getChildByName(name);
	}
	
	public String getAttribute(Element el, String name, String defaultValue, boolean parent)
	{
		return el.getAttribute(name, defaultValue);
	}
	
	public String getAttributeValue(Element el, String attribute, String name, String defaultValue)
	{
		// style="image-rendering:optimizeQuality;opacity:0.613"
		String att = getAttribute(el,"style", "", false);
		String value = "";
		try
		{			
			int st = att.indexOf(name+":");
			if(st==-1)
				return value;
			
			int en = att.indexOf(';', st);
			if(en==-1)
				en = att.indexOf('"', st);
			if(en==-1)
				return value;
			
			value = att.substring(st+1, en);			
		}
		catch(Exception e)
		{
			Gdx.app.log("Solvery", "Error getting value: "+attribute+"="+name+":VALUE;");
		}
		return value;
	}
	
	// gets opacity from image element
	public float getOpacity(Element el)
	{
		float opacity = 1;
		String op = getAttributeValue(el,"style","opacity","1");
		if(!op.equals(""))
		{
			opacity = getFloat(op, 1);
		}
		return opacity;
	}
	
	public String getColor(String s)
	{
		String f = "f:";
		int start = s.indexOf(f); // compressed
		if(start==-1)
		{
			f="fill:";
			start = s.indexOf(f); // not compressed
		}
		int end = s.indexOf(";", start);
		if(end>start)
			return s.substring(start+f.length(), end);
		return "";
	}
	
	public static int getHexValue(char c)
	{
		if(c>='a')
			return c-'a'+10;
		else
			return c-'0';
	}
	
	public static Color getColorFromString(String c)
	{
		if(c.length()>5)
		{
			//Gdx.app.log("Solvery", c);
			float r = getHexValue(c.charAt(0))*16+getHexValue(c.charAt(1));
			float g = getHexValue(c.charAt(2))*16+getHexValue(c.charAt(3));
			float b = getHexValue(c.charAt(4))*16+getHexValue(c.charAt(5));
			r/=256f;
			g/=256f;
			b/=256f;
			//Gdx.app.log("Solvery", "r"+r+"g"+g+"b"+b);
			return new Color(r,g,b,1);
		}
		else
			return new Color(1,1,1,1);
	}
	
	public static float[] getSixFloats(String trans, String search)
	{
		int st = trans.indexOf(search);
		float[] fl = new float[6];
		float xx = 0;
		float yy = 0;
		
		float zz = 0;
		float x2 = 0;
		float y2 = 0;
		float z2 = 0;
		
		if(st!=-1) // jest translate
		{
			int comma = trans.indexOf(",", st);
			int bracket = trans.indexOf(")", st);
			int comma2 = trans.indexOf(",", comma+1);
			try
			{
				
				xx = new Float(trans.substring(st+search.length(), comma));
				yy = new Float(trans.substring(comma+1, comma2));
				comma = comma2;
				comma2 = trans.indexOf(",", comma+1);
				zz = new Float(trans.substring(comma+1, comma2));
				
				comma = comma2;
				comma2 = trans.indexOf(",", comma+1);
				x2 = new Float(trans.substring(comma+1, comma2));
				
				comma = comma2;
				comma2 = trans.indexOf(",", comma+1);
				y2 = new Float(trans.substring(comma+1, comma2));
				
				comma = comma2;
				z2 = new Float(trans.substring(comma+1, bracket));
				
			}
			catch(Exception e)
			{
				
			}
		}
		fl[0] = xx;
		fl[1] = yy;
		fl[2] = zz;
		fl[3] = x2;
		fl[4] = y2;
		fl[5] = z2;
		return fl;
	}
	
	public static float getOneFloat(String trans, String search)
	{
		int st = trans.indexOf(search);
		float xx = 0;
		if(st!=-1) // jest translate
		{
			int comma = trans.indexOf(")", st);
			try
			{
				xx = getFloat(trans.substring(st+search.length(), comma));
				return xx;
			}
			catch(Exception e)
			{
				
			}
		}
		return 0;
	}
	
	public static float[] getTwoFloats(String trans, String search)
	{
		int st = trans.indexOf(search);
		float[] fl = new float[2];
		float xx = 0;
		float yy = 0;
		if(st!=-1) // jest translate
		{
			int comma = trans.indexOf(",", st); // TODO: SVG also allows space to be used instead of ,
			int bracket = trans.indexOf(")", st);
			try
			{
				if(comma!=-1)
				{
					xx = getFloat(trans.substring(st+search.length(), comma));
					yy = getFloat(trans.substring(comma+1, bracket));
				}
				else
				{
					xx = getFloat(trans.substring(st+search.length(), bracket));
					yy = xx; // imporant - because SVG assumes scale(1) = scale(1,1)
				}
			}
			catch(Exception e)
			{
				xx=0;
				yy=0;
				Gdx.app.log("Solvery", "Error getting floats: "+trans+":"+search+"; Assuming 0,0;");
			}
		}
		fl[0] = xx;
		fl[1] = yy;
		return fl;
	}
	
	public static float[] getThreeFloats(String trans, String search)
	{
		int st = trans.indexOf(search);
		float[] fl = new float[3];
		float xx = 0;
		float yy = 0;
		float zz = 0;
		if(st!=-1) // jest translate
		{
			try
			{
				int comma = trans.indexOf(",", st);
				int bracket = trans.indexOf(")", st);
				int comma2 = trans.indexOf(",", comma+1);
				xx = getFloat(trans.substring(st+search.length(), comma));
				yy = getFloat(trans.substring(comma+1, comma2));
				comma = comma2;
				comma2 = trans.indexOf(",", comma+1);
				zz = getFloat(trans.substring(comma+1, bracket));			
			}
			catch(Exception e)
			{
				xx=0;
				yy=0;
				zz=0;
				Gdx.app.log("Solvery", "Error getting floats: "+trans+":"+search+"; Assuming 0,0,0;");
			}
		}
		fl[0] = xx;
		fl[1] = yy;
		fl[2] = zz;
		return fl;
	}
	
	public static int getInt(String t, String beg)
	{
		return getInt(t.substring(beg.length()));
	}
	
	public static int getInt(String trans)
	{
		try
		{
			return new Integer(trans);
		}
		catch(Exception e)
		{
		}
		return 0;
	}
	
	public static float getFloat(String t, String beg)
	{
		return getFloat(t.substring(beg.length()));
	}
	
	public static float getFloat(String trans)
	{
		if(trans.startsWith("--"))
			trans = trans.substring(1);
		try
		{
			return new Float(trans);
		}
		catch(Exception e)
		{
		}
		return 0;
	}
	
	public static float getFloat(String trans, float defaultValue)
	{
		if(trans.startsWith("--"))
			trans = trans.substring(1);
		try
		{
			return new Float(trans);
		}
		catch(Exception e)
		{
		}
		return defaultValue;
	}
	
	// FIELDS 
	
	String layerName;
	public float SVGWidth;
	public float SVGHeight;
	// MAIN FUNCTIONS - the most important part of code
	
	public void getSVGDimentions(Element el)
	{
		String w = getAttribute(el,"width", "1280", false);
		String h = getAttribute(el,"height", "800", false);
		w=w.replace("px", "");
		h=h.replace("px", "");
		SVGWidth =  getFloat(w);
		SVGHeight =  getFloat(h);
	}
	
	public Matrix3 getTransformMatrix(String trans)
	{
		float matrixNew[] = new float[9];
		
		matrixNew[0] = 1;
		matrixNew[1] = 0;
			matrixNew[2] = 0;
		matrixNew[3] = 0;
		matrixNew[4] = 1;
			matrixNew[5] = 0;
		matrixNew[6] = 0;
		matrixNew[7] = 0;
			matrixNew[8] = 1;
			
		if(!trans.equals(""))
		{
			if(isRotation(trans))
			{
				// if is rotation 3 values
				if(trans.contains(","))
				{
					// translate(<cx>, <cy>) rotate(<rotate-angle>) translate(-<cx>, -<cy>)
					float r[] = getRotationFloats(trans);
					Matrix3 t2 = getTransformMatrix("translate("+r[1]+","+r[2]+")");
					Matrix3 rr = getTransformMatrix("rotate("+r[0]+")");
					Matrix3 t1 = getTransformMatrix("translate("+(-r[1])+","+(-r[2])+")");
					/*Gdx.app.log("test", "translate("+r[1]+","+r[2]+")");
					Gdx.app.log("test", "rotate("+r[0]+")");
					Gdx.app.log("test", "translate(-"+r[1]+",-"+r[2]+")");*/
					rr.mulLeft(t2);
					t1.mulLeft(rr);
					return t1;
					
				}
				else
				{
					// -> rotation matrix
					float r = getRotationFloat(trans);
					matrixNew[0] = (float) Math.cos(Math.toRadians(r));
					matrixNew[1] = (float) Math.sin(Math.toRadians(r));
						matrixNew[2] = 0;
					matrixNew[3] = (float) -Math.sin(Math.toRadians(r));
					matrixNew[4] = (float) Math.cos(Math.toRadians(r));
						matrixNew[5] = 0;
					matrixNew[6] = 0;
					matrixNew[7] = 0;
						matrixNew[8] = 1;
				}
			}
			else if(isScale(trans))
			{
				// -> scale matrix
				float fl[] = getScaleFloats(trans);
				matrixNew[0] = fl[0];
				matrixNew[1] = 0;
					matrixNew[2] = 0;
				matrixNew[3] = 0;
				matrixNew[4] = fl[1];
					matrixNew[5] = 0;
				matrixNew[6] = 0;
				matrixNew[7] = 0;
					matrixNew[8] = 1;
			}
			else if(isTranslate(trans))
			{
				// -> translate matrix matrix(1 0 0 1 x y)
				float fl[] = getTranslateFloats(trans);
				matrixNew[0] = 1;
				matrixNew[1] = 0;
					matrixNew[2] = 0;
				matrixNew[3] = 0;
				matrixNew[4] = 1;
					matrixNew[5] = 0;
				matrixNew[6] = fl[0];
				matrixNew[7] = fl[1];
					matrixNew[8] = 1;
			}
			else if(isMatrix(trans))
			{
				// just read matrix
				float fl[] = getMatrixFloats(trans);
				matrixNew[0] = fl[0];
				matrixNew[1] = fl[1];
					matrixNew[2] = 0;
				matrixNew[3] = fl[2];
				matrixNew[4] = fl[3];
					matrixNew[5] = 0;
				matrixNew[6] = fl[4];
				matrixNew[7] = fl[5];
					matrixNew[8] = 1;
			}			
		}
		return new Matrix3(matrixNew);
	}
	
	public Vector2 deltaTransformPoint(Matrix3 matrix, Vector2 point)  
	{
        float dx = point.x * matrix.val[MA] + point.y * matrix.val[MC] + 0;
        float dy = point.x * matrix.val[MB] + point.y * matrix.val[MD] + 0;
        return new Vector2(dx, dy);
    }
	
	final static int MA = 0;
	final static int MB = 3;
	final static int MC = 1;
	final static int MD = 4;
	final static int ME = 6;
	final static int MF = 7;
	
	public Matrix3 translate(float x, float y)
	{
		// translate to x,y of the object
		float[] matrixNew = new float[9];
		
		matrixNew[0] = 1;
		matrixNew[1] = 0;
			matrixNew[2] = 0;
		matrixNew[3] = 0;
		matrixNew[4] = 1;
			matrixNew[5] = 0;
		matrixNew[6] = x;
		matrixNew[7] = y;
			matrixNew[8] = 1;
		
		return new Matrix3(matrixNew);
	}
	
	public void getScale(Matrix3 matrix, Vector2 scale)
	{
		matrix.getScale(scale);
        float determinant = ((matrix.val[0]*matrix.val[4] - matrix.val[3]*matrix.val[1])<0) ? -1 : 1;
        if(determinant<0)
        {
        	if (matrix.val[0] < matrix.val[4])
        		scale.x = -scale.x;
        	else
        		scale.y = -scale.y;
        }
	}
	
	public void loadElement(Element el, float[] matrixOld)
	{
		String elname = el.getName();
		if(elname.equals("svg"))
			getSVGDimentions(el);
		
		int count = el.getChildCount();
			
		// magic for transforms and matrixes, don't touch unless you know what you are doing
		String trans = getAttribute(el,"transform", "", false);
		String title = getTitle(el);
		float elementX = getFloat(el.getAttribute("x", "0"));
		float elementY = getFloat(el.getAttribute("y", "0"));
		float elementWidth = getFloat(getAttribute(el,"width", "", false));
		float elementHeight = getFloat(getAttribute(el,"height", "", false));
		Vector2 origin = new Vector2(elementWidth/2, elementHeight/2);
		Vector2 scale = new Vector2(1,1);
		Vector2 scaleOld = new Vector2(1,1);
        Vector2 translate = new Vector2();
        float rr; // rotation
        
		Matrix3 matrix = getTransformMatrix(trans);
		float oldR = 0;
		if(matrixOld!=null) // is a matrix
		{
			Matrix3 mOld = new Matrix3(matrixOld);
			oldR = mOld.getRotation();
			getScale(mOld,scaleOld);
			
			if(scaleOld.y<0||scaleOld.x<0)
			{
				origin.rotate(oldR);
				matrix.mulLeft(translate(-origin.x,-origin.y));
				origin.rotate(-oldR);
				matrix.mulLeft(mOld);
				matrix.mul(translate(elementX,elementY));
				matrix.translate(origin.x,origin.y);	
			}
			else
			{
				origin.rotate(-oldR);
				matrix.mulLeft(translate(-origin.x,-origin.y));
				origin.rotate(oldR);
				matrix.mulLeft(mOld);
				matrix.mul(translate(elementX,elementY));
				matrix.translate(origin.x,origin.y);			
			}
		}
		else
		{
			matrix.mulLeft(translate(-origin.x,-origin.y));
			matrix.mul(translate(elementX,elementY));
			matrix.translate(origin.x,origin.y);
		}
		
		if(count!=0)
			for(int i=0; i<count; i++)
				loadElement(el.getChild(i),matrix.getValues());
		
		// important magic for width,height and x,y
		// inspired by this: http://stackoverflow.com/questions/16359246/how-to-extract-position-rotation-and-scale-from-matrix-svg
		// and this: http://www.w3.org/TR/css3-transforms/
		        
		matrix.getScale(scale);
        rr = matrix.getRotation();
        
        // magic which I don't understand
        getScale(matrix,scale);
        if(scale.y<0&&scaleOld.y>0)
        {
   			Vector2 det = new Vector2(0,elementHeight*Math.signum(scale.y)*2); // because y flip uses top border of the image 
   			det.rotate(rr);
   			matrix.translate(det);
        }
        
        if(scaleOld.y<0||scaleOld.x<0)
        {
   			Vector2 det = new Vector2(0,scaleOld.y*elementHeight/scale.y); // because... well, I don't know why
   			if(scale.y<0)
   				det.rotate(rr+180);
   			else if(scale.x<0)
   				det.rotate(rr);
   			else
   			{
   				if(scaleOld.x<0)
   					det.rotate(-rr+180);
   				else
   					det.rotate(-rr);
   			}
   			   			
   			matrix.translate(det);
        }
        
        matrix.getTranslation(translate);
        
        float width = elementWidth*scale.x;
        float height = elementHeight*scale.y;
        float yyy = SVGHeight-translate.y-height;
        float xxx = translate.x;
        
        if(title.startsWith("testing"))
        {
        	Gdx.app.log("test", "oldR:"+oldR+" sx:"+scaleOld.x+" sy:"+scaleOld.y);
        	Gdx.app.log("test", ":::"+title+":"+xxx+","+yyy+":"+rr+" "+width+"x"+height+" rr:"+rr+" scalex:"+scale.x+" scaley:"+scale.y);
        }
        rr = -rr;
		if(width<0)
			rr=rr+180;
		if(rr<0)
			rr+=360;

	   if(title.startsWith("testing"))
			Gdx.app.log("test", ":::newrr:"+rr);
		   
		if(elname.equals("path")) // path
		{
			String d = el.getAttribute("d", "");
			newPath(parsePathToArray(d, elementX, elementY, SVGHeight), el, title);
		}
		else if(isText(elname)) // text
		{
			Element e = getChild(el,"tspan");
			if(e==null)
				return;
			
			String text;
			if(e.getText()==null)
				text = "";
			else
				text = e.getText().trim();
			
			// font-size as height! - width not set unfortunately
			// example: font-size:44.03109741px;
			String style = getAttribute(el,"style", "", false);
			String styles[] = style.split("\\;");
			Color color = new Color(1,1,1,1);
			if(styles!=null&&styles.length>0)
			{
				for(int i=0; i<styles.length; i++)
				{
					if(isStyle(styles[i], "font-size"))
					{
						String stylesdata[] = styles[i].split("\\:");
						stylesdata[1] = stylesdata[1].replace("px", "");
						stylesdata[1] = stylesdata[1].replace(";", "");
						height = getFloat(stylesdata[1].trim());
					}
					else if(isStyle(styles[i], "fill"))
					{
						//fill:#effffa
						String stylesdata[] = styles[i].split("\\:");
						stylesdata[1] = stylesdata[1].replace("#", "");
						stylesdata[1] = stylesdata[1].replace(";", "");
						color = getColorFromString(stylesdata[1].trim());
					}
				}
			}
			newText(text, el, xxx, yyy, width, height, rr, color);
		}
		else if(isImage(elname))  // obraz
		{
			String name = getImageName(getAttribute(el, "xlink:href", "", false));
			newImage(name, el, xxx, yyy, width, height, rr);			
		}
		else if(isRect(elname)) // obraz
		{
			Element title2 = getChild(el, "title");
			if(title2!=null)
				newRect(title2.getText(), el, xxx, yyy, width, height, rr);
			else
				newRect("", el, xxx, yyy, width, height, rr);
		}
	}
	
	
	// actions to override
	
	abstract public void newImage(String name, Element el, float xxx, float yyy,
			float width, float height, float rr);
	
	abstract public void newRect(String name, Element el, float xxx, float yyy,
			float width, float height, float rr);

	abstract public void newText(String text, Element el, float xxx, float yyy,
			float width, float height, float rr, Color color);
	
	abstract public void newPath(Array<Vector2> path, Element el, String title);
}