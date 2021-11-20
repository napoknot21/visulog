package up.visulog.ui;

import up.visulog.analyzer.AnalyzerResult;
import up.visulog.config.PluginConfig;

import java.util.HashMap;

public interface UIController {
    String value="";
    HashMap<String, String> NAME_TO_PLUGIN_NAME = initializeNameToPluginName();
    HashMap<String, PluginConfig> PLUGINS = initializePlugins();

    AnalyzerResult run(); //Execute le plugin Todo: amelioration de cette partie

    String toHtml(AnalyzerResult result);


    private static HashMap<String, String> initializeNameToPluginName() { //(Nom du bouton -> Nom du plugin)
        HashMap<String,String> NAME_TO_PLUGIN_NAME = new HashMap<>();
        NAME_TO_PLUGIN_NAME.put("Commits","countCommits");
        NAME_TO_PLUGIN_NAME.put("Merge Commits","countMergeCommits");



        return NAME_TO_PLUGIN_NAME;
    }

    private static HashMap<String, PluginConfig>  initializePlugins () { //Initialise la liste des plugins disponible
        //Fixme: A adapter a notre implementation de PluginConfig
        HashMap<String, PluginConfig> PLUGINS = new HashMap<>();

        for (String name : NAME_TO_PLUGIN_NAME.values()) {
            PLUGINS.put(name,new PluginConfig());
        }
        return PLUGINS;
    }

}
