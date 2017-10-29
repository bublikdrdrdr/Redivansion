package tk.ubublik.redivansion.gamelogic.units.objects;

/**
 * Created by Bublik on 09-Oct-17.
 */

public class RoadState{
    private boolean front;//x+
    private boolean back;//x-
    private boolean right;//z+
    private boolean left;//z-

    public RoadState(){
        this(false, false, false, false);
    }

    public RoadState(boolean front, boolean back, boolean right, boolean left) {
        this.front = front;
        this.back = back;
        this.right = right;
        this.left = left;
    }

    public boolean isFront() {
        return front;
    }

    public void setFront(boolean front) {
        this.front = front;
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public final String[] names = {"Lone", "End", "Straight", "Turn", "Triple", "Quadriple"};

    //from 0 to 1 (full 360 degrees)
    public float getRotation(){
        switch (getModelNameIndex()){
            case 0: return 0f;
            case 1:
                if (front) return 0;
                else if (right) return 0.25f;
                else if (back) return 0.5f;
                else return 0.75f;
            case 2:
                if (front) return 0; else return 0.25f;
            case 3:
                if (left&&front) return 0;
                else if (right&&front) return 0.25f;
                else if (back&&right) return 0.5f;
                else return 0.75f;
            case 4: if (!left) return 0.25f;
            else if (!front) return 0.5f;
            else if (!right) return 0.75f;
            else return 0;
            case 5: return 0;
            default: throw new IllegalArgumentException("Illegal model name index: "+getModelNameIndex());
        }
    }

    public int getModelNameIndex(){
        switch (getConnectionsCount()){
            case 0: return 0;
            case 1: return 1;
            case 2: if ((front&&back) || (left&&right))
                return 2;
            else return 3;
            case 3: return 4;
            case 4: return 5;
            default: throw new IllegalArgumentException("Illegal connections count: "+getConnectionsCount());

        }
    }

    public String getModelName(){
        return names[getModelNameIndex()];
    }

    public int getConnectionsCount() {
        int res = 0;
        if (front) res++;
        if (back) res++;
        if (left) res++;
        if (right) res++;
        return res;
    }
}