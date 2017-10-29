package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by SomeOne on 27.10.2017.
 */

public class GUIElements {

    private float x;
    private float y;
    private float w;
    private float h;
    private String picPath;
    private float counter;

    public String name;
    public String text;
    public Picture p;
    public BitmapText txt;

    public GUIElements(String name, String text, float x, float y, float w, float h, String path){
        this.name = name;
        this.text = text;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.picPath = path;
        setPicture();
        if(!text.equals(""))
            setText();
    }

    private void setPicture(){
        p = new Picture(name);
        p.move(0, 0, -1);
        p.setPosition(x, y);
        p.setWidth(w);
        p.setHeight(h);
        p.setImage(StaticAssetManager.getAssetManager(), picPath, false);
    }

    private void setText(){
        BitmapFont fnt = StaticAssetManager.loadFont("Interface/Fonts/Default.fnt");
        txt = new BitmapText(fnt, false);
        txt.setBox(new Rectangle(x, y, w, h));
        txt.setSize(fnt.getPreferredSize() * 2f);
        txt.setAlignment(BitmapFont.Align.Center);
        txt.setVerticalAlignment(BitmapFont.VAlign.Center);
        txt.setText(text);
        txt.setLocalTranslation(0, txt.getHeight(), 0);
        setPicture();
    }

    public boolean touchCheck(float tX, float tY){
        if((tX >= x) && (tX <= (x+w)) && (tY >= y) && (tY <= (y+h))) {
            return true;
        }
        else return false;
    }
}