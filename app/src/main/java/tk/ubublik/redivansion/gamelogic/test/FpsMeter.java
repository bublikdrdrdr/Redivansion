package tk.ubublik.redivansion.gamelogic.test;

/**
 * Created by Bublik on 26-Oct-17.
 */

public final class FpsMeter {

    private static final FpsMeter instance = new FpsMeter();

    private FpsMeter(){

    }

    public static FpsMeter getInstance(){
        return instance;
    }

    long nano = System.nanoTime();
    long fpsCounter = 0;
    public void logFps(){
        if (System.nanoTime()-nano >= 500000000L){
            System.out.println("RENDER FPS: "+fpsCounter*2);
            fpsCounter = 0;
            nano = System.nanoTime();
        }
        fpsCounter++;
    }

    long lastLogicShow = System.currentTimeMillis();
    public void logLogic(long nanos){
        if (System.currentTimeMillis()-lastLogicShow>=500) {
            long elapsed = System.nanoTime() - nanos;
            //yes, I got divide by zero exception
            if (elapsed > 0)
                System.out.println("LOGIC FPS: " + (1000000000L / elapsed));
            else
                System.out.println("LOGIC FPS: over9000");
            lastLogicShow = System.currentTimeMillis();
        }
    }

    private long lastCustomTime;
    private long beginTime;
    private boolean allowCustom = false;
    public void beginCustom(){
        if (System.currentTimeMillis()-lastCustomTime>500){
            allowCustom = true;
            lastCustomTime = System.currentTimeMillis();
            System.out.println("----------------------");
            beginTime = System.nanoTime();
        } else {
            allowCustom = false;
        }
    }

    public void logCustom(String name){
        if (!allowCustom) return;
        long current = System.nanoTime()-beginTime;
        String fps;
        if (current==0) fps = "over9000"; else
            fps = Long.toString(1000000000L/current);
        System.out.println(name+" FPS: "+fps);
        beginTime = System.nanoTime();
    }


}
