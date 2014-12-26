package falstad;

import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/*import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;*/

/**
 * graphics encapsulated here. 
 * Turtle Time activation does a complete graphical rehaul.
 * @author VJ
 *
 */

@SuppressLint("HandlerLeak")
public class MazePanel extends View{
	
	private static final String LOGTAG = "MazePanel";
	Canvas gc;//canvas to draw on
	Bitmap bmp;//bitmap to hold the canvas drawings
	Paint paint ;//paints which change the canvas
	boolean turtleTime = globals.maze.getTurtleTime();
	
	//Rect src = new Rect(0,0,1500,1500);//used for setting the screen size 
	Rect dest = new Rect(0,0,1700,1700);//used for setting the screen size(originally 1700 1700 for samsung)(1200 1200 for school)
	
	 public Handler handler = new Handler(){
		 @Override
		 public void handleMessage(Message message) {
		 //Log.i("!!!", "Handler was called");
		 //if (c != null){
			 Log.v(LOGTAG, "canvas invalidated hopefully");
			 postInvalidate();
			 
		 //}
		 }
		 };
	
	
	public MazePanel(Context context, AttributeSet attrs) {
		super(context,attrs);
		Log.v(LOGTAG,"MazePanel Constructed...");
		paint = new Paint();//paint initialized
		this.setFocusable(false) ;
	}
	@Override
	public void onDraw(Canvas canvas) {
		
		canvas.drawBitmap(bmp, null, dest ,null);
	}
	public void setBitmap(){
		Log.v(LOGTAG,"Bitmap set");
		int w = 700, h=700;
		Bitmap.Config conf = Bitmap.Config.ARGB_8888;
		
		bmp = Bitmap.createBitmap(w,h,conf);
		gc = new Canvas(bmp);
	}
//PROJECT 5 AND PROJECT 6 - new methods to encapsulate graphics
	
	public void setColorWhite() {
		paint.setColor(Color.WHITE);
	}
	public void setColorRed() {
		paint.setColor(Color.RED);
	}
	public void setColorBlue() {
		paint.setColor(Color.BLUE);
	}
	public void setColorBlack() {
		if(turtleTime){
			paint.setColor(Color.rgb(138, 108, 74));
		}
		else{
		paint.setColor(Color.BLACK);
		}
		
	}
	public void setColorYellow() {
		paint.setColor(Color.YELLOW);
	}
	public void setColorOrange() {
		paint.setColor(Color.CYAN);//may need to switch to another method of pulling colors
	}
	public void fillRect(int i, int j, int viewWidth, int viewHeight) {
		paint.setStyle(Paint.Style.FILL);
		gc.drawRect(i,j,viewWidth,viewHeight,paint);
		
		
	}
	/*public FontMetrics getFontMetrics() {
		return gc.getFontMetrics();
	}
	public void drawString(String str, int i, int ypos) {
		gc.drawString(str, i, ypos);
	}*/ // methods for strings no longer needed since there is no more code based title, generating, and finish states
	public void setColorDarkGray() {
		paint.setColor(Color.DKGRAY);
	}
	//fill polygon is properly called, and builds the walls in the maze
	public void fillPolygon(int[] xps, int[] yps, int i) {
		paint.setStyle(Paint.Style.FILL);
		Path path = new Path();
		path.reset();
		path.moveTo(xps[0], yps[0]);
		for(int j =1;j<i;j++)
			path.lineTo(xps[j], yps[j]);
		path.close();
		gc.drawPath(path, paint);
		if(turtleTime){
			Paint black = new Paint();
			black.setColor(Color.BLACK);
			black.setStyle(Paint.Style.STROKE);
			gc.drawPath(path, black);
		}
	}
	public void setColor(Seg seg) {
		if(turtleTime){
			//for walls
			paint.setColor(Color.rgb(209, 182, 137));
		}else{
		int col = Color.rgb(seg.col[0], seg.col[1], seg.col[2]);
		paint.setColor(col);
		}
	}
	public void drawLine(int nx1, int ny1, int nx2, int ny12) {
		gc.drawLine(nx1, ny1, nx2, ny12,paint);
	}
	public void setColorWhiteorGray(boolean hasWall) {
		if(hasWall){
			paint.setColor(Color.WHITE);
		}
		else{
			paint.setColor(Color.GRAY);
		}
	}
	/*public void setFontLarge() {
		gc.setFont(largeBannerFont);
	}
	public void setFontSmall() {
		gc.setFont(smallBannerFont);
	}
	public void setFontMedium() {
		gc.setFont(mediumBannerFont);
	}
	public void setFontMetrics(){
	 fm = gc.getFontMetrics();
	}
	public void centerString(MazePanel g, FontMetrics fm, String str, int ypos) {
		g.drawString(str, (Constants.VIEW_WIDTH-fm.stringWidth(str))/2, ypos);
	}*/
	public static int getRGB(int[] col) {
		int color = Color.rgb(col[0] , col[1] , col[2]);
		return color;
		}
	public static int[] getColorArray(int col) {
			int[] color = new int[3];
			color[0] = Color.red(col);
			color[1] = Color.green(col);
			color[2] = Color.blue(col);
			return color;
	}
	
	

	
}
