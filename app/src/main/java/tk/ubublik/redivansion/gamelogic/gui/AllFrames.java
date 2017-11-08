package tk.ubublik.redivansion.gamelogic.gui;

import tk.ubublik.redivansion.MainActivity;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class AllFrames {

    public Frame main, select, add;

    public AllFrames() {
        initMain();
        initAdd();
        initSelect();
    }

    private void initMain(){
        main = new Frame("main");
        //name, text (null if none), (in percents) x, y, wight, height, square (if true then width = height, picture path
        main.addElement("addSmthing", "+", 0, 80, 20, 20, true, "Textures/Monkey.png");
        main.addElement("showSelect", "Select", 0, 60, 20, 20, true, "Textures/bg.jpg");
        main.addElement("remove", "Remove", 0, 40, 20, 20, true, "Textures/bg.jpg");
    }

    private void initSelect(){
        select = new Frame("select");
        Element.z = 0;
        //select.addTransparent("bg", null, 0, 0, 100, 100, false, "Textures/2.png");
        select.addElement("bg", null, 0, 0, 100, 100, false, "Textures/2.png");
        select.addElement("bg", null, 30, 10, 40, 80, false, "Textures/Monkey.png");
        select.addElement("selectTree", "Select Tree", 32, 72, 36, 16, false, "Textures/bg.jpg");
        select.addElement("selectOffice", "Select Office", 32, 52, 36, 16, false, "Textures/bg.jpg");
        select.addElement("selectClear", "Clear Select", 32, 32, 36, 16, false, "Textures/bg.jpg");
        select.addElement("selectClose", "Close", 32, 12, 36, 16, false, "Textures/bg.jpg");
    }

    private void initAdd(){
        add = new Frame("add");
        Element.z = 0;
        add.addElement("bg", null, 30, 5, 40, 90, false, "Textures/Monkey.png");
        add.addElement("addTree", "Add Tree", 32, 78, 36, 16, false, "Textures/bg.jpg");
        add.addElement("addOffice", "Add Office", 32, 60, 36, 16, false, "Textures/bg.jpg");
        add.addElement("addRoad", "Add Road", 32, 42, 36, 16, false, "Textures/bg.jpg");
        add.addElement("setRoadPoints", "Add Road Points", 32, 24, 36, 16, false, "Textures/bg.jpg");
        add.addElement("addClose", "Close", 32, 6, 36, 16, false, "Textures/bg.jpg");
    }


}
