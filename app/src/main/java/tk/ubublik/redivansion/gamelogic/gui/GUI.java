package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;

import tk.ubublik.redivansion.gamelogic.Main;
import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.lifecycle.FreeplayLifecycle;
import tk.ubublik.redivansion.gamelogic.lifecycle.LevelLifecycle;
import tk.ubublik.redivansion.gamelogic.lifecycle.LifecycleType;
import tk.ubublik.redivansion.gamelogic.lifecycle.MenuLifecycle;
import tk.ubublik.redivansion.gamelogic.lifecycle.TestLifecycle;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.GUIListener;
import tk.ubublik.redivansion.gamelogic.utils.MenuListener;
import tk.ubublik.redivansion.gamelogic.utils.TouchInputHook;

public class GUI implements TouchInputHook {

    public static final int CLICK_OFFSET = 5;
    public GUIListener guiListener;
    public MenuListener menuListener;
    public Screen guiScreen;
    public CameraControl cameraControl;
    public static AllFrames frames = new AllFrames();
    public static float startX;
    public static float startY;
    private float prevY= startY;
    private boolean touchedGUI = false;
    public TouchListener touchListener;

    public GUI(Node guiNode, GUIListener guiListener, CameraControl cameraControl, Frame frame) {
        this.guiListener = guiListener;
        menuListener = null;
        this.cameraControl = cameraControl;
        guiScreen = new Screen(frame.frameName, guiNode, frame, this);
        setStatusChanged(0,0, true);
        setTime(0);
        initListener();
    }

    public GUI(Node guiNode, MenuListener menuListener, Frame frame) {
        this.menuListener = menuListener;
        guiListener = null;
        guiScreen = new Screen(frame.frameName, guiNode, frame, this);
        initListener();
    }

    public void onUpdate() {

    }

    public void setTime(long time){
        for(Element element:frames.main.elements) {
            if (element.p.getName().equals("time")) {
                if(time == -666)
                    element.txt.setText("Time: UNLIMITED");
                else{
                    int sec = (int)time % 10;
                    if(sec > 2 && sec < 8)
                        time += 5 - sec;
                    else if(sec > 7)
                        time += 10 - sec;
                    else time -= sec;
                    element.txt.setText("Time: " + (time));
                }
                return;
            }
        }
    }

    public void setStatusChanged(int population, int money, boolean grow){
        for(Element element:frames.main.elements){
            if(element.p.getName().equals("population")){
                element.txt.setText("Population: " + population);
            }
            if(element.p.getName().equals("money")){
                element.txt.setText("Money: " + money);
            }
        }
    }

    private void initListener() {
        touchListener = new TouchListener() {
            @Override
            public void onTouch(String name, TouchEvent event, float tpf) {
                if (name.equals(Main.BACK_PRESS_EVENT)) {
                    switch (event.getType()) {
                        case KEY_UP:
                            if(guiScreen.getActiveFrame().frameName.equals("main")){
                                guiListener.pauseTime(true);
                                guiScreen.showFrame(AllFrames.menu);
                            }
                            else if(guiScreen.getActiveFrame().frameName.equals("mainMenu")){
                                guiScreen.removeFrame();
                                menuListener.exit();
                            }
                            else TouchEvents.closeFrame(guiScreen);
                            break;
                    }
                }
            }
        };
    }

    @Override
    public boolean touchCaptured(TouchEvent touchEvent, float tfp) {
        if(!guiScreen.isEmpty()){
        if(touchEvent.getType() == TouchEvent.Type.DOWN){
            this.startX = touchEvent.getX();
            this.startY = touchEvent.getY();
            touchedGUI = guiScreen.touchEvent(startX, startY, guiListener, menuListener, touchEvent);
        }
        else if(!touchedGUI && touchEvent.getType() == TouchEvent.Type.LONGPRESSED){
            showInfo(touchEvent);
        }

        else if(touchEvent.getType() == TouchEvent.Type.SCROLL &&
                (guiScreen.getActiveFrame().frameName.equals("levelMenu"))){
            if(touchEvent.getY() > prevY
                    )
                guiScreen.getActiveFrame().scrollElements(touchEvent.getDeltaY());
            else if(touchEvent.getY() < prevY
                    )
                guiScreen.getActiveFrame().scrollElements(touchEvent.getDeltaY());
            prevY = touchEvent.getY();

            if(guiScreen.getActiveFrame().elements.get(guiScreen.getActiveFrame().elements.size()-1).y > 3*Element.dY)
                guiScreen.getActiveFrame().scrollElements(3*Element.dY - guiScreen.getActiveFrame().elements.get(guiScreen.getActiveFrame().elements.size()-1).y);
            else if(guiScreen.getActiveFrame().elements.get(2).y < 89*Element.dY)
                guiScreen.getActiveFrame().scrollElements(89*Element.dY - guiScreen.getActiveFrame().elements.get(2).y);
        }

        else if(touchEvent.getType() == TouchEvent.Type.UP && touchedGUI){
            guiScreen.getActiveFrame().touchedElem = false;
            guiScreen.getActiveFrame().removeTouch();
            guiScreen.touchEvent(touchEvent.getX(), touchEvent.getY(), guiListener, menuListener, touchEvent);
            touchedGUI = false;
        }}
        return touchedGUI;
    }


    private void showInfo(TouchEvent touchEvent){
        Vector2f clickScreen = new Vector2f(touchEvent.getX(),touchEvent.getY());
        Vector3f origin    = cameraControl.cam.getWorldCoordinates(clickScreen, 0f).clone();
        Vector3f direction = cameraControl.cam.getWorldCoordinates(clickScreen, 1f);
        direction.subtractLocal(origin).normalizeLocal();
        Ray ray = new Ray(origin, direction);
        CollisionResults results = new CollisionResults();
        Quad quad = new Quad(9000,9000);
        Geometry gem = new Geometry("quad", quad);
        gem.move(4500,0,-4500);
        gem.rotate(FastMath.HALF_PI, FastMath.TWO_PI, FastMath.PI);
        gem.collideWith(ray, results);
        if (results.size() > 0) {
            CollisionResult closest = results.getClosestCollision();
            WorldObject object = guiListener.getWorldMap().getObject(guiListener.getMapRenderer().worldPointToMap(closest.getContactPoint(),1));
            guiListener.objectSelected(object);
            if(object!=null){
                cameraControl.saveCameraPosition();
                AllFrames.initInfo(object);
                guiScreen.showFrame(AllFrames.info);
                float x = object.getPosition().x + 1f + object.getSize()/3f, y = object.getPosition().y - 1f + object.getSize()/3f;
                float delta = cameraControl.cam.getLocation().getY() / (cameraControl.cam.getDirection().getY() * -1f);
                Vector3f fullDirection = cameraControl.cam.getDirection().mult(delta);
                cameraControl.currentFoV = cameraControl.minFov;
                cameraControl.setFov();
                cameraControl.cam.setLocation(new Vector3f(x, 0, y).subtract(fullDirection));

            }}
    }


}
