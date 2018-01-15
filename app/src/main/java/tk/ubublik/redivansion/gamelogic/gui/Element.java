package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.ui.Picture;

import tk.ubublik.redivansion.MainActivity;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by SomeOne on 27.10.2017.
 */

public class Element {

    public static final float dX = MainActivity.getScreenWidth() * 0.01f;
    public static final float dY = MainActivity.getScreenHeight() * 0.01f;
    public static float z = -1;

    public float x, y, zp, zt = 0, w, h;
    private TextPosition align;
    public boolean interactive = false;

    public enum TextPosition{Left_VCenter, Right_VCenter, Center_VCenter, Left_VTop, Right_VTop, Center_VTop}

    public Picture p;
    public BitmapText txt;

    public Element() {
    }

    public Element(String name, String text, boolean interactive, TextPosition align, float x, float y, float w, float h,
                   boolean square, String path) {
        new Element();
        this.interactive = interactive;
        this.align = align;

        this.x = x * dX;
        this.y = y * dY;
        this.h = h * dY;
        if (square)
            this.w = w * dY;
        else
            this.w = w * dX;
        zp = z;
        setPicture(name, path);
        z += 0.1;
        if (text != null)
            zt = z;
            setText(text);
        z += 0.1;
    }

    private void setPicture(String name, String path) {
        p = new Picture(name);
        p.move(0, 0, z);
        p.setPosition(x, y);
        p.setWidth(w);
        p.setHeight(h);
        p.setImage(StaticAssetManager.getAssetManager(), path, true);
    }

    private void setText(String text) {
        BitmapFont fnt = StaticAssetManager.loadFont("Fonts/final.fnt");
        txt = new BitmapText(fnt, false);
        setTextPosition();
        float size = 5f*dY;
        txt.setSize(size);
        txt.setText(text);
        txt.setLocalTranslation(0, txt.getHeight(), z);
    }

    public void setTextPosition(){
        float t = h/3.2f;
        if(align == null){
            txt.setBox(new Rectangle(x+1*dX,y+t,w-2*dX,h/1.7f));
            txt.setAlignment(BitmapFont.Align.Center);
            txt.setVerticalAlignment(BitmapFont.VAlign.Center);
        }else switch (align){
            case Left_VCenter:
                txt.setBox(new Rectangle(x+1*dX, y+t, w-1*dX, h/2));
                txt.setAlignment(BitmapFont.Align.Left);
                txt.setVerticalAlignment(BitmapFont.VAlign.Center);
                break;
            case Right_VCenter:
                txt.setBox(new Rectangle(x+1*dX, y+t, w-2*dX, h/2));
                txt.setAlignment(BitmapFont.Align.Right);
                txt.setVerticalAlignment(BitmapFont.VAlign.Center);
                break;
            case Left_VTop:
                txt.setBox(new Rectangle(x+2*dX, y-1*dY, w-3*dX, h/1.2f));
                txt.setAlignment(BitmapFont.Align.Left);
                txt.setVerticalAlignment(BitmapFont.VAlign.Top);
                break;
            case Right_VTop:
                txt.setBox(new Rectangle(x+1*dX, y, w-2*dX, h/1.2f));
                txt.setAlignment(BitmapFont.Align.Right);
                txt.setVerticalAlignment(BitmapFont.VAlign.Top);
                break;
            case Center_VTop:
                txt.setBox(new Rectangle(x+1*dX, y, w-2*dX, h/1.2f));
                txt.setAlignment(BitmapFont.Align.Center);
                txt.setVerticalAlignment(BitmapFont.VAlign.Top);
                break;
            default:
                txt.setBox(new Rectangle(x+1*dX, y+t, w-2*1*dX, h/1.7f));
                txt.setAlignment(BitmapFont.Align.Center);
                txt.setVerticalAlignment(BitmapFont.VAlign.Center);
                break;
        }
    }

    public boolean touchCheck(float tX, float tY) {
        if ((tX >= x) && (tX <= (x + w)) && (tY >= y) && (tY <= (y + h))) {
            return true;
        } else return false;
    }
}

