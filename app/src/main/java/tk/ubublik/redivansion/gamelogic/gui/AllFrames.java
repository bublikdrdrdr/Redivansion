package tk.ubublik.redivansion.gamelogic.gui;

import tk.ubublik.redivansion.MainActivity;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class AllFrames {

    public static Frame main, add, build;

    public AllFrames() {
        initMain();
        initAdd();
        initBuild();
    }

    private void initMain(){
        main = new Frame("main");
        //name, text (null if none), (in percents) x, y, wight, height, square (if true then width = height), picture path, transparent
        main.addElement("addSmthing", "+", 0, 80, 20, 20, true, "Textures/bg.jpg", false);
        main.addElement("remove", "Remove", 0, 60, 20, 20, true, "Textures/bg.jpg", false);
    }

    private void initBuild(){
        build = new Frame("build");
        build.addElement("build", "+", 0, 80, 20, 20, true, "Textures/bg.jpg", false);
        build.addElement("cancel", "X", 0, 60, 20, 20, true, "Textures/bg.jpg", false);
    }

    private void initAdd(){
        add = new Frame("add");
        Element.z = 0;
        add.addElement("bg", null, 0, 0, 100, 100, false, "Textures/2.png", true);
        add.addElement("bg", null, 30, 10, 40, 80, false, "Textures/Monkey.png", false);
        add.addElement("addTree", "Add Tree", 32, 72, 36, 16, false, "Textures/bg.jpg", false);
        add.addElement("addOffice", "Add Office", 32, 52, 36, 16, false, "Textures/bg.jpg", false);
        add.addElement("setRoadPoints", "Add Road Points", 32, 32, 36, 16, false, "Textures/bg.jpg", false);
        add.addElement("addClose", "Close", 32, 12, 36, 16, false, "Textures/bg.jpg", false);
    }

    public static Frame getFrame(String name){
        switch (name){
            case "main":
                return main;
            case "add":
                return add;
            default: return main;
        }
    }


}
