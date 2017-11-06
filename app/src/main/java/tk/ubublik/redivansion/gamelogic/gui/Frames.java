package tk.ubublik.redivansion.gamelogic.gui;

import tk.ubublik.redivansion.MainActivity;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class Frames {

    float dX = MainActivity.getScreenWidth() * 0.01f;
    float dY = MainActivity.getScreenHeight() * 0.01f;

    public GUIFrames main, select, add;

    public Frames() {
        initMain();
        initAdd();
        initSelect();
    }

    private void initMain(){
        main = new GUIFrames("main");
        main.addElement("addSmthing", "+", 0, (80 * dY), (20 * dY), (20 * dY), "Textures/Monkey.png");
        main.addElement("showSelect", "Select", 0, (60 * dY), (20 * dY), (20 * dY), "pics/bg.jpg");
        main.addElement("remove", "Remove", 0, (40 * dY), (20 * dY), (20 * dY), "pics/bg.jpg");
    }

    private void initSelect(){
        select = new GUIFrames("select");
        select.addElement("bg", "", (30 * dX), (10 * dY), (40 * dX), (80 * dY), "Textures/Monkey.png");
        select.addElement("selectTree", "Select Tree", (32 * dX), (72 * dY), (36 * dX), (16 * dY), "pics/bg.jpg");
        select.addElement("selectOffice", "Select Office", (32 * dX), (52 * dY), (36 * dX), (16 * dY), "pics/bg.jpg");
        select.addElement("selectClear", "Clear Select", (32 * dX), (32 * dY), (36 * dX), (16 * dY), "pics/bg.jpg");
        select.addElement("selectClose", "Close", (32 * dX), (12 * dY), (36 * dX), (16 * dY), "pics/bg.jpg");
    }

    private void initAdd(){
        add = new GUIFrames("add");
        add.addElement("bg", "", (30 * dX), (5 * dY), (40 * dX), (90 * dY), "Textures/Monkey.png");
        add.addElement("addTree", "Add Tree", 32 * dX, 78 * dY, 36 * dX, 16 * dY, "pics/bg.jpg");
        add.addElement("addOffice", "Add Office", 32 * dX, 60 * dY, 36 * dX, 16 * dY, "pics/bg.jpg");
        add.addElement("addRoad", "Add Road", 32 * dX, 42 * dY, 36 * dX, 16 * dY, "pics/bg.jpg");
        add.addElement("setRoadPoints", "Add Road Points", 32 * dX, 24 * dY, 36 * dX, 16 * dY, "pics/bg.jpg");
        add.addElement("addClose", "Close", 32 * dX, 6 * dY, 36 * dX, 16 * dY, "pics/bg.jpg");
    }


}
