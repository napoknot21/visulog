package up.visulog.config;

import java.io.Serializable;
import java.util.Map;

// TODO: define what this type should be (probably a Map: settingKey -> settingValue)
public class PluginConfig implements Serializable { //Pour que les configurations puissent être stockées dans un fichier et réutilisées
    public final Map<String, String> settings = null;
    /*Encore à déterminer ce que value va représenter : j'hésite entre soit un path ou une commande (?) je ne sais pas à voir*/
    public PluginConfig(){}

    public PluginConfig(PluginConfig p){
        /*Fait juste une copie de p*/
        for (Map.Entry<String,String>entry: p.settings.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            this.settings.put(key,value);
        }
    }


}
