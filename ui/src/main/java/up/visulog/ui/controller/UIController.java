package up.visulog.ui.controller;

import up.visulog.analyzer.AnalyzerResult;
import up.visulog.config.PluginConfig;

import java.util.HashMap;

public interface UIController {
    String value = ""; // Value courante du controller
    HashMap<String, String> NAME_TO_PLUGIN_NAME = initializeNameToPluginName();
    // Map (Nom sur l'UI -> nom du plugin)
    HashMap<String, PluginConfig> PLUGINS = initializePlugins();
    //Map (Nom du plugin -> pluginConfig)


    private static HashMap<String, String> initializeNameToPluginName() { //Initialise la map automatiquement en fonction
        HashMap<String, String> NAME_TO_PLUGIN_NAME                     //En fonction des boutons
                = new HashMap<>(MethodButton.BUTTON_NAME_TO_PLUGIN_NAME);
        for (String plugin : MethodButton.BUTTON_NAME_TO_PLUGIN_NAME.values()) {
            for (String filter : MethodRadioButton.RADIO_BUTTON_NAME.values()) {
                NAME_TO_PLUGIN_NAME.put(plugin + filter, (plugin + filter).replace(" ", ""));
            }
        }
        return NAME_TO_PLUGIN_NAME;
    }


    private static HashMap<String, PluginConfig> initializePlugins() { //Initialise la liste des plugins disponible
        //Fixme: A adapter a notre implementation de PluginConfig
        HashMap<String, PluginConfig> PLUGINS = new HashMap<>();

        for (String name : NAME_TO_PLUGIN_NAME.values()) {
            PLUGINS.put(name, new PluginConfig());
        }
        return PLUGINS;
    }

    AnalyzerResult run(); //Execute le plugin Todo: amelioration de cette partie

    String toHtml(AnalyzerResult result);

}
