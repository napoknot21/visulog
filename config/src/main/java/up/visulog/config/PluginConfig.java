package up.visulog.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PluginConfig implements Serializable {
    public final Map<String, List<String>> config = new HashMap<>();
    public final String[] period = new String[2];

    public PluginConfig(){
    }

    public PluginConfig(HashMap<String, List<String>> pluginConfig) {
        for (Map.Entry<String, List<String>>entry: pluginConfig.entrySet()){
            String key = entry.getKey();
            List<String> value = entry.getValue();
            this.config.put(key,value);
        }
    }

    public PluginConfig(String beginning, String end){
        period[0] = beginning;
        period[1] = end;
    }

    public List<String> get(String key) {
        return config.get(key);
    }
}
