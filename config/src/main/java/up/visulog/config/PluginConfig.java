package up.visulog.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PluginConfig implements Serializable { //Pour que les configurations puissent être stockées dans un fichier et réutilisées
    public final Map<String, ArrayList<String>> config = new HashMap<>();


    /*Encore à déterminer ce que value va représenter : j'hésite entre soit un path ou une commande (?) je ne sais pas à voir*/
    public PluginConfig(){}



    public PluginConfig(HashMap<String, ArrayList<String>> pluginConfig) {
        for (Map.Entry<String,ArrayList<String>>entry: pluginConfig.entrySet()){
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            this.config.put(key,value);
        }
    }

    public ArrayList<String> get(String key) {
        return config.get(key);
    }
}
