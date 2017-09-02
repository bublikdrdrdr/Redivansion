package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;

/**
 * Created by Bublik on 01-Sep-17.
 */

public class MainLifecycle implements Lifecycle {

    private Lifecycle currentLifecycle;
    private SimpleApplication simpleApplication;
    private boolean done = false;

    public MainLifecycle(SimpleApplication simpleApplication) {
        //todo: use simpleApplication to get rootNode, guiNode, listeners etc.
        this.simpleApplication = simpleApplication;
        currentLifecycle = new MainLoadingLifecycle();
    }

    @Override
    public LifecycleType getType() {
        return null;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void update() {
        currentLifecycle.update();
        if (currentLifecycle.isDone()) {
            switch (currentLifecycle.getType()) {
                case MAIN_LOADING:
                    currentLifecycle = new MenuLifecycle();
                    break;
                case MENU:
                    processMenu();
                    break;
                case LEVEL_LOADING:
                    currentLifecycle = new LevelLifecycle(((LevelLoadingLifecycle) currentLifecycle).getLevelNumber());
                    break;
                case LEVEL: currentLifecycle = new MainLoadingLifecycle(); break;
            }
        }
    }

    private void processMenu() {
        switch (((MenuLifecycle) currentLifecycle).menuResult) {
            case START_LEVEL:
                currentLifecycle = new LevelLoadingLifecycle(((MenuLifecycle) currentLifecycle).startLevelNumber);
                break;
            case START_TUTORIAL:
                currentLifecycle = new TutorialLoadingLifecycle();
                break;
            case EXIT:
                done = true;
                break;
        }
    }
}
