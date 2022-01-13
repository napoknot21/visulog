package up.visulog.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PluginConfig implements Serializable {
    public final Map<String, ArrayList<String>> config = new HashMap<>();
    public final String[] period = new String[2];

    public PluginConfig(){}

    public PluginConfig(HashMap<String, ArrayList<String>> pluginConfig) {
        for (Map.Entry<String,ArrayList<String>>entry: pluginConfig.entrySet()){
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            this.config.put(key,value);
        }
    }

    public PluginConfig(String beginning, String end){
        period[0] = beginning;
        period[1] = end;
    }

    public ArrayList<String> get(String key) {
        return config.get(key);
    }
}
