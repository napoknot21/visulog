package up.visulog.ui.model;

import up.visulog.config.PluginConfig;

import java.util.HashMap;

public interface MapRelations {
    HashMap<String, String> BUTTON_NAME_TO_PLUGIN_NAME = initButtonNameToPluginName();
    //Map (Nom du bouton -> nom du plugin)
    HashMap<String, String> RADIO_BUTTON_NAME = initializeRadioButtonName();
    //Map (nom du filtre -> nom du plugin)
    HashMap<String, String> NAME_TO_PLUGIN_NAME = initializeNameToPluginName();
    // Map (Nom sur l'UI -> nom du plugin)
    HashMap<String, PluginConfig> PLUGINS = initializePlugins();
    //Map (Nom du plugin -> pluginConfig)


    private static HashMap<String, String> initializeNameToPluginName() { //Initialise la map automatiquement en fonction
        HashMap<String, String> NAME_TO_PLUGIN_NAME                     //En fonction des boutons
                = new HashMap<>(BUTTON_NAME_TO_PLUGIN_NAME);
        for (String plugin : BUTTON_NAME_TO_PLUGIN_NAME.values()) {
            for (String filter : RADIO_BUTTON_NAME.values()) {
                NAME_TO_PLUGIN_NAME.put(plugin + filter, (plugin + filter).replace(" ", ""));
            }
        }
        return NAME_TO_PLUGIN_NAME;
    }

    private static HashMap<String, String> initializeRadioButtonName() { //initialise la map
        HashMap<String, String> RADIO_BUTTON_NAME = new HashMap<>();
        RADIO_BUTTON_NAME.put("Par jour", "PerDay");
        //Todo: a remplir selon les filtres

        return RADIO_BUTTON_NAME;
    }

    private static HashMap<String, String> initButtonNameToPluginName() { //Initialise la map
        HashMap<String, String> BUTTON_NAME_TO_PLUGIN_NAME = new HashMap<>();
        BUTTON_NAME_TO_PLUGIN_NAME.put("Commits", "countCommitsPerAuthor");
        BUTTON_NAME_TO_PLUGIN_NAME.put("Merge Commits", "countMergeCommits");
        //Todo: a remplir selon les plugins

        return BUTTON_NAME_TO_PLUGIN_NAME;
    }

    private static HashMap<String, PluginConfig> initializePlugins() { //Initialise la liste des plugins disponible
        //Fixme: A adapter a notre implementation de PluginConfig
        HashMap<String, PluginConfig> PLUGINS = new HashMap<>();

        for (String name : NAME_TO_PLUGIN_NAME.values()) {
            PLUGINS.put(name, new PluginConfig());
        }
        return PLUGINS;
    }
}
