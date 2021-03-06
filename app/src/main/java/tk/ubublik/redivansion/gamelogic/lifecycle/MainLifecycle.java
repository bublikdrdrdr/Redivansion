package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;

/**
 * Created by Bublik on 01-Sep-17.
 */

public class MainLifecycle extends Lifecycle {

    private Lifecycle currentLifecycle;
    private boolean done = false;

    public MainLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
        currentLifecycle = new MainLoadingLifecycle(simpleApplication);
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
                    currentLifecycle = new MenuLifecycle(simpleApplication);
                    break;
                case MENU:
                    processMenu();
                    break;
                case LEVEL_LOADING:
                    currentLifecycle = new LevelLifecycle(((LevelLoadingLifecycle) currentLifecycle).getLevelNumber(), simpleApplication);
                    break;
                case LEVEL:
                    currentLifecycle = new MainLoadingLifecycle(simpleApplication);
                    break;
                case TUTORIAL_LOADING:
                    currentLifecycle = new TutorialLifecycle(simpleApplication);
                    break;
                case TUTORIAL:
                    currentLifecycle = new MainLoadingLifecycle(simpleApplication);
                    break;
                case FREEPLAY_LOADING:
                    currentLifecycle = new FreeplayLifecycle(simpleApplication);
                    break;
                case FREEPLAY:
                    currentLifecycle = new MainLoadingLifecycle(simpleApplication);
                    break;
            }
        }
    }

    private void processMenu() {
        switch (((MenuLifecycle) currentLifecycle).menuResult) {
            case START_LEVEL:
                currentLifecycle = new LevelLoadingLifecycle(((MenuLifecycle) currentLifecycle).startLevelNumber, simpleApplication);
                break;
            case START_TUTORIAL:
                currentLifecycle = new TutorialLoadingLifecycle(simpleApplication);
                break;
            case START_FREEPLAY:
                currentLifecycle = new FreeplayLoadingLifecycle(simpleApplication);
                break;
            case EXIT:
                done = true;
                break;
            case IDLE:
                break;
        }
    }
}
