package tk.ubublik.redivansion.gamelogic.gui;

import tk.ubublik.redivansion.MainActivity;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class AllFrames {

    public static Frame main, add, build, menu;

    public AllFrames() {
        initMain();
        initMenu();
        initAdd();
        initBuild();
    }

    private void initMain(){
        main = new Frame("main");
        Element.z = -2;
        //name, text (null if none), align (0 center, 1 left, 2 right), (in percents) x, y, wight, height, square (if true then width = height), picture path, transparent
        main.addElement("bg", null, 0, 0, 85, 40, 15, false, "Textures/btn1.png", false);
        main.addElement("bg", null, 0, 2, 87, 11, 11, true, "Textures/time.png", true);
        main.addElement("time", "Time: ", 1, 11, 87, 26, 11, false, "Textures/btn2.png", false);
        main.addElement("population", "Population: ", 1, 74, 89, 26, 11, false, "Textures/btn1.png", false);
        main.addElement("money", "Money: ", 1, 74, 78, 26, 11, false, "Textures/btn1.png", false);
        main.addElement("addSmthing", "+", 0, 0, 65, 20, 20, true, "Textures/btn1.png", false);
        main.addElement("remove", "Remove", 0, 0, 45, 20, 20, true, "Textures/btn1.png", false);
        main.addElement("menu", "Menu", 0, 0, 0, 20, 20, true, "Textures/btn1.png", false);
    }

    private void initBuild(){
        Element.z = -1;
        build = new Frame("build");
        build.addElement("build", "+", 0, 0, 65, 20, 20, true, "Textures/btn1.png", false);
        build.addElement("cancel", "X", 0, 0, 45, 20, 20, true, "Textures/btn1.png", false);
    }

    private void initMenu(){
        Element.z = 0;
        menu = new Frame("menu");
        menu.addElement("bg", null, 0, 0, 0, 100, 100, false, "Textures/2.png", true);
        menu.addElement("bg", null, 0, 30, 35, 40, 29, false, "Textures/Monkey.png", false);
        menu.addElement("bg", "Menu", 1, 30, 61, 33, 10, false, "Textures/bg.jpg", false);
        menu.addElement("menuClose", "X", 0, 63, 61, 7, 10, false, "Textures/btn1.png", false);
        menu.addElement("save", "Save", 0, 32, 49, 36, 10, false, "Textures/btn1.png", false);
        menu.addElement("removeSave", "Remove last save", 0, 32, 37, 36, 10, false, "Textures/btn1.png", false);
    }

    private void initAdd(){
        add = new Frame("add");
        Element.z = 0;
        add.addElement("bg", null, 0, 0, 0, 100, 100, false, "Textures/2.png", true);
        add.addElement("bg", null, 0, 30, 5, 40, 90, false, "Textures/Monkey.png", false);
        add.addElement("bg", "Buildings", 1, 30, 85, 33, 10, false, "Textures/bg.jpg", false);
        add.addElement("addClose", "X", 0, 63, 85, 7, 10, false, "Textures/btn1.png", false);
        add.addElement("addTree", "Add Tree", 0, 32, 73, 36, 10, false, "Textures/btn1.png", false);
        add.addElement("addOffice", "Add Office", 0, 32, 61, 36, 10, false, "Textures/btn1.png", false);
        add.addElement("addHouse", "Add House", 0, 32, 49, 36, 10, false, "Textures/btn1.png", false);
        add.addElement("addPower", "Add Powerplant", 0, 32, 37, 36, 10, false, "Textures/btn1.png", false);
        add.addElement("setRoadPoints", "Add Road Points", 0, 32, 25, 36, 10, false, "Textures/btn1.png", false);
        add.addElement("addPrev", "<--", 0, 32, 7, 16, 10, false, "Textures/btn2.png", false);
        add.addElement("addNext", "-->", 0, 52, 7, 16, 10, false, "Textures/btn2.png", false);
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
