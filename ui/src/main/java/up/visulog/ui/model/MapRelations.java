package up.visulog.ui.model;

import up.visulog.analyzer.Analyzer;
import up.visulog.config.PluginConfig;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public interface MapRelations {
    GregorianCalendar GREGORIAN_CALENDAR = new GregorianCalendar();
    int CURRENT_YEAR = GREGORIAN_CALENDAR.get(Calendar.YEAR);
    int CURRENT_MONTH = GREGORIAN_CALENDAR.get(Calendar.MONTH) + 1;
    int CURRENT_DAY = GREGORIAN_CALENDAR.get(Calendar.DAY_OF_MONTH);
    /**
     * Map (nom du filtre -> nom du plugin)
     */
    HashMap<String, String> RADIO_BUTTON_NAME = initializeRadioButtonName();
    /**
     * Map (Nom du bouton -> nom du plugin)
     */
    HashMap<String, String> BUTTON_NAME_TO_PLUGIN_NAME = initButtonNameToPluginName();
    /**
     * Map (Nom sur l'UI -> nom du plugin)
     */
    HashMap<String, String> NAME_TO_PLUGIN_NAME = initializeNameToPluginName();
    /**
     * Map (Nom du plugin -> pluginConfig)
     */
    HashMap<String, PluginConfig> PLUGINS = initializePlugins();

    /**
     * Map (nom du plugin -> legende du graphique)
     */
    HashMap<String, String[]> LEGEND = initializeLegend();

    /**
     * Initialise la map automatiquement en fonction des boutons
     *
     * @return la valeur de NAME_TO_PLUGIN_NAME
     */
    private static HashMap<String, String> initializeNameToPluginName() {
        HashMap<String, String> NAME_TO_PLUGIN_NAME
                = new HashMap<>(BUTTON_NAME_TO_PLUGIN_NAME);
        for (String plugin : BUTTON_NAME_TO_PLUGIN_NAME.values()) {
            for (String filter : RADIO_BUTTON_NAME.values()) {
                NAME_TO_PLUGIN_NAME.put(plugin + filter, (plugin + filter).replace(" ", ""));
            }
        }
        return NAME_TO_PLUGIN_NAME;
    }

    /**
     * Initialise la map des filtres de plugin (nom sur l'ui -> valeur du filtre)
     *
     * @return la valeur de RADIO_BUTTON_NAME
     */
    private static HashMap<String, String> initializeRadioButtonName() {
        GregorianCalendar calendar = new GregorianCalendar();
        HashMap<String, String> RADIO_BUTTON_NAME = new HashMap<>();
        RADIO_BUTTON_NAME.put("Par defaut", "");
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        RADIO_BUTTON_NAME.put("Sur le dernier jour", createDate(calendar));
        calendar = (GregorianCalendar) GREGORIAN_CALENDAR.clone();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        RADIO_BUTTON_NAME.put("Sur la derniere semaine", createDate(calendar));
        calendar = (GregorianCalendar) GREGORIAN_CALENDAR.clone();
        calendar.add(Calendar.DAY_OF_MONTH, -14);
        RADIO_BUTTON_NAME.put("Sur les 14 derniers jours", createDate(calendar));
        calendar = (GregorianCalendar) GREGORIAN_CALENDAR.clone();
        calendar.add(Calendar.MONTH, -1);
        RADIO_BUTTON_NAME.put("Sur le dernier mois", createDate(calendar));
        calendar = (GregorianCalendar) GREGORIAN_CALENDAR.clone();
        calendar.add(Calendar.MONTH, -3);
        RADIO_BUTTON_NAME.put("Sur le dernier trimestre", createDate(calendar));

        return RADIO_BUTTON_NAME;
    }

    /**
     * Cree ue date selon le format des perPeriod
     *
     * @param year  est l'annee cherchee
     * @param month est le mois cherchee
     * @param day   ets le jour cherchee
     * @return la date voulue sous le format de perPeriod
     */
    private static String createDate(int year, int month, int day) {
        return "PerPeriod-" + year + '/' + (month + 1) + '/' + day + "-" + MapRelations.CURRENT_YEAR + '/' + MapRelations.CURRENT_MONTH + '/' + MapRelations.CURRENT_DAY;
    }

    /**
     * @param calendar reprensent le calendrier cherche
     * @return la date voulue sous le format de perPeriod
     */
    private static String createDate(Calendar calendar) {
        return createDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Initialise la map des plugins generaux (nom du boutons -> Plugin)
     *
     * @return la valeur de BUTTON_NAME_TO_PLUGIN_NAME
     */
    private static HashMap<String, String> initButtonNameToPluginName() {
        HashMap<String, String> BUTTON_NAME_TO_PLUGIN_NAME = new HashMap<>();
        BUTTON_NAME_TO_PLUGIN_NAME.put("Commits", "CountCommitsPerAuthor");
        BUTTON_NAME_TO_PLUGIN_NAME.put("Merge Commits", "CountMergeCommitsPerAuthor");
        BUTTON_NAME_TO_PLUGIN_NAME.put("Nombres de lignes", "CountLinesPerAuthor");
        BUTTON_NAME_TO_PLUGIN_NAME.put("Commits par semaine", "CountCommitsPerWeek");

        return BUTTON_NAME_TO_PLUGIN_NAME;
    }

    /**
     * Initialise la liste des plugins disponible
     *
     * @return la valeur de PLUGINS
     */
    private static HashMap<String, PluginConfig> initializePlugins() { //
        HashMap<String, PluginConfig> PLUGINS = new HashMap<>();

        for (String name : NAME_TO_PLUGIN_NAME.values()) {
            String[] args = name.split("-");
            try {
                Analyzer.findClassPlugins(name.split("-")[0]);
                if (args.length == 3) PLUGINS.put(name, new PluginConfig(args[1], args[2]));
                else PLUGINS.put(name, new PluginConfig());
            } catch (Exception ignored) {
            }
        }
        return PLUGINS;
    }


    /**
     * Initialise la map des legendes
     *
     * @return la valeur de LEGEND
     */
    private static HashMap<String, String[]> initializeLegend() {
        HashMap<String, String[]> LEGEND = new HashMap<>();
        LEGEND.put("Commits", new String[]{"Nombre de commits par personne"});
        LEGEND.put("Commits par semaine", new String[]{"Nombre de commits par semaine"});
        LEGEND.put("Merge Commits", new String[]{"Nombre de merge commits par personne"});
        LEGEND.put("Nombres de lignes", new String[]{"Nombre de lignes ajoutées par personne", "Nombre de lignes retirées par personne"});
        LEGEND.put("research", new String[]{"Statistique de la recherche"});
        LEGEND.put("Par defaut", new String[]{"Commits par auteur"});
        LEGEND.put("Sur le dernier jour", new String[]{"Commits par auteur sur le dernier jour"});
        LEGEND.put("Sur la derniere semaine", new String[]{"Commits par auteur sur la derniere semaine"});
        LEGEND.put("Sur les 14 derniers jours", new String[]{"Commits par auteur sur les 14 derniers jours"});
        LEGEND.put("Sur le dernier mois", new String[]{"Commits par auteur sur le dernier mois"});
        LEGEND.put("Sur le dernier trimestre", new String[]{"Commits par auteur sur le dernier trimestre"});
        return LEGEND;
    }

}
