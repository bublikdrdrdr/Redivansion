package tk.ubublik.redivansion.gamelogic.utils;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import tk.ubublik.redivansion.gamelogic.graphics.Model;

/**
 * Created by Bublik on 29-Aug-17.
 */

//for *.crm (Compressed Redivansion Model files)
public class CustomModelLoader implements AssetLoader {
    @Override
    public Object load(AssetInfo assetInfo) throws IOException {
        InputStream inputStream = assetInfo.openStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        return new Model(bytes);
    }
}
