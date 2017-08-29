package tk.ubublik.redivansion.gamelogic.graphics;

import java.util.HashMap;
import java.util.Objects;

import tk.ubublik.redivansion.gamelogic.exceptions.ModelNotFoundException;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 29-Aug-17.
 */

public class ModelManager {

    private HashMap<String, Model> models = new HashMap<>();

    public void clearModels(){
        models.clear();
    }

    public void setModel(String name, Model model){
        models.put(name, model);
    }

    public void loadModel(String name) throws ModelNotFoundException{
        Object o = StaticAssetManager.getAssetManager().loadAsset("Models/"+name);
        if (o instanceof Model){
            setModel(name, (Model)o);
        } else{
            throw new IllegalArgumentException("Asset is not an instance of "+Model.class.getName());
        }
    }

    public Model getModel(String s) {
        return models.get(s);
    }
}
