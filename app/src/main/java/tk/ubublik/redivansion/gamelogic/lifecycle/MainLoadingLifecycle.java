package tk.ubublik.redivansion.gamelogic.lifecycle;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class MainLoadingLifecycle extends LoadingLifecycle {

    private volatile boolean done = false;

    public MainLoadingLifecycle(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO: 02-Sep-17 load gui nodes to NodesCache
                done = true;
            }
        });
        thread.start();
    }

    @Override
    public LifecycleType getType() {
        return LifecycleType.MAIN_LOADING;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void update() {
        //todo show animation
    }
}
