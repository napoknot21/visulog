package up.visulog.ui.model;

import up.visulog.analyzer.Analyzer;
import up.visulog.config.PluginConfig;

import java.util.*;

public interface MapRelations {
    GregorianCalendar GREGORIAN_CALENDAR = new GregorianCalendar();
    int CURRENT_YEAR = GREGORIAN_CALENDAR.get(Calendar.YEAR);
    int CURRENT_MONTH = GREGORIAN_CALENDAR.get(Calendar.MONTH)+1;
    int CURRENT_DAY = GREGORIAN_CALENDAR.get(Calendar.DAY_OF_MONTH);
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
        GregorianCalendar calendar = new GregorianCalendar();
        HashMap<String, String> RADIO_BUTTON_NAME = new HashMap<>();
        RADIO_BUTTON_NAME.put("Par defaut", "");
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        RADIO_BUTTON_NAME.put("Sur le dernier jour", createDate(calendar));
        calendar = (GregorianCalendar) GREGORIAN_CALENDAR.clone();
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        RADIO_BUTTON_NAME.put("Sur la derniere semaine", createDate(calendar));
        calendar = (GregorianCalendar) GREGORIAN_CALENDAR.clone();
        calendar.add(Calendar.DAY_OF_MONTH,-14);
        RADIO_BUTTON_NAME.put("Sur les 14 derniers jours", createDate(calendar));
        calendar = (GregorianCalendar) GREGORIAN_CALENDAR.clone();
        calendar.add(Calendar.MONTH,-1);
        RADIO_BUTTON_NAME.put("Sur le dernier mois",createDate(calendar));
        calendar = (GregorianCalendar) GREGORIAN_CALENDAR.clone();
        calendar.add(Calendar.MONTH,-3);
        RADIO_BUTTON_NAME.put("Sur le dernier trimestre",createDate(calendar));
        //Todo: a remplir selon les filtres

        return RADIO_BUTTON_NAME;
    }


    private static String createDate(int year, int month, int day) {
        return "PerPeriod-"+year+'/'+(month+1)+'/'+day+"-"+MapRelations.CURRENT_YEAR+'/'+MapRelations.CURRENT_MONTH+'/'+MapRelations.CURRENT_DAY;
    }

    private static String createDate (Calendar calendar) {
        return createDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    private static HashMap<String, String> initButtonNameToPluginName() { //Initialise la map
        HashMap<String, String> BUTTON_NAME_TO_PLUGIN_NAME = new HashMap<>();
        BUTTON_NAME_TO_PLUGIN_NAME.put("Commits", "countCommitsPerAuthor");
        BUTTON_NAME_TO_PLUGIN_NAME.put("Merge Commits", "countMergeCommitsPerAuthor");
        BUTTON_NAME_TO_PLUGIN_NAME.put("Nombres de lignes", "countLinesPerAuthor");
        //Todo: a remplir selon les plugins

        return BUTTON_NAME_TO_PLUGIN_NAME;
    }

    private static HashMap<String, PluginConfig> initializePlugins() { //Initialise la liste des plugins disponible
        //Fixme: A adapter a notre implementation de PluginConfig
        HashMap<String, PluginConfig> PLUGINS = new HashMap<>();

        for (String name : NAME_TO_PLUGIN_NAME.values()) {
            String[] args = name.split("-");
            try {
                Analyzer.findClassPlugins(name.split("-")[0]);
                if (args.length == 3) PLUGINS.put(name, new PluginConfig(args[1],args[2]));
                else PLUGINS.put(name,new PluginConfig());
            } catch (Exception ignored) {}
        }
        return PLUGINS;
    }
}
