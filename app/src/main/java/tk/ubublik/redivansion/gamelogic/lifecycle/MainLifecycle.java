package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;

/**
 * Created by Bublik on 01-Sep-17.
 */

public class MainLifecycle extends Lifecycle {

    private Lifecycle currentLifecycle;
    private boolean done = false;

    public MainLifecycle(SimpleApplication SimpleApplication) {
        //todo: use SimpleApplication to get rootNode, guiNode, listeners etc.
        super(SimpleApplication);
        currentLifecycle = new MainLoadingLifecycle(SimpleApplication);
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
                    currentLifecycle = new MenuLifecycle(SimpleApplication);
                    break;
                case MENU:
                    processMenu();
                    break;
                case LEVEL_LOADING:
                    currentLifecycle = new LevelLifecycle(((LevelLoadingLifecycle) currentLifecycle).getLevelNumber(), SimpleApplication);
                    break;
                case LEVEL:
                    currentLifecycle = new MainLoadingLifecycle(SimpleApplication);
                    break;
                case TUTORIAL_LOADING:
                    currentLifecycle = new TutorialLifecycle(SimpleApplication);
                    break;
                case TUTORIAL:
                    currentLifecycle = new MainLoadingLifecycle(SimpleApplication);
                    break;
            }
        }
    }

    private void processMenu() {
        switch (((MenuLifecycle) currentLifecycle).menuResult) {
            case START_LEVEL:
                currentLifecycle = new LevelLoadingLifecycle(((MenuLifecycle) currentLifecycle).startLevelNumber, SimpleApplication);
                break;
            case START_TUTORIAL:
                currentLifecycle = new TutorialLoadingLifecycle(SimpleApplication);
                break;
            case EXIT:
                done = true;
                break;
        }
    }
}
