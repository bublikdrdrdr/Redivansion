package tk.ubublik.redivansion.gamelogic.gui;

/**
 * Created by SomeOne.
 */

//Треба було посидіти подумати над динамічним конструктором йопт

public class TutorialFrames {

    /*
    1. Level loaded: greetings, about game.
    2. Goal, population, time, money.
    3. Camera move and zoom.
    4. Add building button, choose house, add house;
    5. Info about object, icons, upgrade;
    6. Road importance and building;
    7. Ending
     */

    public static String[] tutorialFrames = {"about", "goals", "cameraMove", "cameraZoom", "buildAddHouse", "buildAddRoad1",
            "buildAddRoad2", "objectInfo", "objectInfo2", "roadInfo", "finish", "population", "time", "money", "buildMenu1", "buildMenu2",
            "buildChoose1", "buildChoose2", "buildAdd1", "buildAdd2"};

    public static Frame blank() {
        Element.z = 20;
        Frame frame = new Frame("blank");
        frame.addElement("bg", null, false, null, 0, 65, 20, 20, true, "Textures/btn2.png");
        frame.addElement("bg", null, false, null, 0, 65, 20, 20, true, "Textures/addX.png");
        frame.addElement("bg", "Remove", false, null, 0, 45, 20, 20, true, "Textures/btn2.png");
        frame.addElement("bg", "Menu", false, null, 0, 0, 20, 20, true, "Textures/btn2.png");
        return frame;
    }

    public static Frame frame(String name) {
        Element.z = 110;
        Frame frame = new Frame(name);
        String text = "";
        switch (name) {
            case "about":
                text = "Welcome to Redivansion! In this tutorial you'll now learn about everything and how to play the game.";
                break;
            case "goals":
                text = "Every level goal is to reach certain population or build something before the time ends.";
                break;
            case "cameraMove":
                text = "To move the camera around, just move your finger over the screen. Try now, it's easy!";
                break;
            case "cameraZoom":
                text = "\"Pinch\" gesture allows you to zoom the camera. Suddenly, huh? Try it right now!";
                break;
            case "buildAddHouse":
                text = "Now choose position where you want to build it and press \"Add\" button. Look at the colour of square.";
                break;
            case "buildAddRoad1":
                text = "Road building requires two steps. First, choose a start point where the road will start.";
                break;
            case "buildAddRoad2":
                text = "Next, lead the road to any from four directions you want and choose the end point of your road.";
                break;
            case "objectInfo":
                text = "Great! Now tap and hold finger over the new building to get more information about it.";
                break;
            case "roadInfo":
                text = "Roads are very important. Every single object must be connected to the main road to work properly.";
                break;
            case "finish":
                text = "Now you know most important things about Redivansion. Try to beat some levels. Good luck!";
                break;
        }
        frame.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png");
        frame.addElement("bg", null, false, null, 30, 23, 40, 45, false, "Textures/menubg.png");
        frame.addElement("bg", text, false, null, 31, 35, 38, 31, false, "Textures/2.png");
        frame.addElement("next", "Next", true, null, 31, 24, 38, 10, false, "Textures/btnLong1.png");
        return frame;
    }

    public static Frame population() {
        Element.z = 100;
        Frame frame = new Frame("population");
        frame.addElement("bg", null, false, null, 0, 0, 100, 89, false, "Textures/2.png");
        frame.addElement("bg", null, false, null, 0, 89, 74, 11, false, "Textures/2.png");
        frame.addElement("bg", null, false, null, 30, 23, 40, 45, false, "Textures/menubg.png");
        frame.addElement("bg", "Your current population is shown here.", false, null, 31, 35, 38, 31, false, "Textures/2.png");
        frame.addElement("next", "Next", true, null, 31, 24, 38, 10, false, "Textures/btnLong1.png");
        return frame;
    }

    public static Frame time() {
        Element.z = 100;
        Frame frame = new Frame("time");
        frame.addElement("bg", null, false, null, 0, 0, 100, 85, false, "Textures/2.png");
        frame.addElement("bg", null, false, null, 46, 85, 60, 15, false, "Textures/2.png");
        frame.addElement("bg", null, false, null, 30, 23, 40, 45, false, "Textures/menubg.png");
        frame.addElement("bg", "Level ends when timer reaches zero. Here you can see how many time you still have.", false, null, 31, 35, 38, 31, false, "Textures/2.png");
        frame.addElement("next", "Next", true, null, 31, 24, 38, 10, false, "Textures/btnLong1.png");
        return frame;
    }

    public static Frame money() {
        Element.z = 100;
        Frame frame = new Frame("money");
        frame.addElement("bg", null, false, null, 0, 0, 100, 78, false, "Textures/2.png");
        frame.addElement("bg", null, false, null, 0, 89, 100, 11, false, "Textures/2.png");
        frame.addElement("bg", null, false, null, 0, 78, 74, 11, false, "Textures/2.png");
        frame.addElement("bg", null, false, null, 30, 23, 40, 45, false, "Textures/menubg.png");
        frame.addElement("bg", "You build and upgrade everything with money. Here is information about your budget.", false, null, 31, 35, 38, 31, false, "Textures/2.png");
        frame.addElement("next", "Next", true, null, 31, 24, 38, 10, false, "Textures/btnLong1.png");
        return frame;
    }

    public static Frame buildMenu(boolean road) {
        Element.z = 100;
        String text = road ? "To build the road, open build menu again." : "Now you will learn how to build something. First, go to the building menu by pressing this highlighted button.";
        Frame frame = new Frame("buildMenu" + (road ? "2" : "1"));
        frame.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png");
        frame.addElement("plus", null, true, null, 0, 65, 20, 20, true, "Textures/btn1.png");
        frame.addElement("next", null, true, null, 0, 65, 20, 20, true, "Textures/addX.png");
        frame.addElement("bg", null, false, null, 30, 34, 40, 39, false, "Textures/menubg.png");
        frame.addElement("bg", text, false, null, 31, 35, 38, 36, false, "Textures/2.png");

        return frame;
    }

    public static Frame buildChoose(boolean road) {
        Element.z = 100;

        Frame frame = new Frame("buildChoose" + (road ? "2" : "1"));
        frame.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/tutorbg.png");
        frame.addElement("bg", null, false, null, 30, 5, 40, 90, false, "Textures/2.png");
        if (road) {
            frame.addElement("next", "Add Road Points", true, null, 32, 25, 36, 10, false, "Textures/btnLong1.png");
            frame.addElement("bg", null, false, null, 30, 10, 40, 13, false, "Textures/menubg.png");
            frame.addElement("bg", "Choose road", false, null, 31, 11, 38, 11, false, "Textures/2.png");
        } else {
            frame.addElement("next", "Add House", true, null, 32, 49, 36, 10, false, "Textures/btnLong1.png");
            frame.addElement("bg", null, false, null, 30, 16, 40, 30, false, "Textures/menubg.png");
            frame.addElement("bg", "Choose object to build. For the first time we will build a house where people of our city will live.", false, null, 31, 17, 38, 28, false, "Textures/2.png");
        }
        return frame;
    }

    public static Frame buildAdd(boolean road) {
        Element.z = 100;
        Frame frame = new Frame("buildAdd" + (road ? "2" : "1"));
        frame.addElement("plus", null, true, null, 0, 65, 20, 20, true, "Textures/btn1.png");
        frame.addElement("next", null, true, null, 0, 65, 20, 20, true, "Textures/addX.png");
        frame.addElement("bg", null, false, null, 0, 45, 20, 20, true, "Textures/btn2.png");
        frame.addElement("bg", null, false, null, 0, 45, 20, 20, true, "Textures/closeX.png");
        return frame;
    }

    public static Frame objectInfo2() {
        Element.z = 100;
        Frame frame = new Frame("objectInfo2");
        frame.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/tutorbg.png");
        frame.addElement("bg", null, false, null, 0, 0, 60, 100, false, "Textures/2.png");
        frame.addElement("bg", null, false, null, 60, 90, 40, 10, false, "Textures/2.png");
        frame.addElement("bg", null, false, null, 10, 23, 40, 52, false, "Textures/menubg.png");
        frame.addElement("bg", "Here you can see next info: params status, upgrade button, description and, if object has some, special params.", false, null, 11, 35, 38, 39, false, "Textures/2.png");
        frame.addElement("next", "Next", true, null, 11, 24, 38, 10, false, "Textures/btnLong1.png");
        return frame;
    }
}