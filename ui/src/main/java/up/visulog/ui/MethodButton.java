package up.visulog.ui;

import up.visulog.analyzer.Analyzer;
import up.visulog.analyzer.AnalyzerResult;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;
import javafx.scene.control.Button;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.HashMap;

public class MethodButton extends Button { //Classe Button permettant de lancer les plugins a partir des boutons

    final String value;
    static HashMap<String, String> NAME_TO_PLUGIN_NAME = initializeNameToPluginName();
    static HashMap<String, PluginConfig> PLUGINS = initializePlugins();
    static int posX=0;

    private static HashMap<String, String> initializeNameToPluginName() { //(Nom du bouton -> Nom du plugin)
        NAME_TO_PLUGIN_NAME = new HashMap<>();
        NAME_TO_PLUGIN_NAME.put("Commits","countCommits");
        NAME_TO_PLUGIN_NAME.put("Merge Commits","countMergeCommits");



        return NAME_TO_PLUGIN_NAME;
    }
    private static HashMap<String, PluginConfig>  initializePlugins () { //Initialise la liste des plugins disponible
        //Fixme: A adapter a notre implementation de PluginConfig
        PLUGINS = new HashMap<>();

        for (String name : NAME_TO_PLUGIN_NAME.values()) {
            PLUGINS.put(name,new PluginConfig());
        }
        return PLUGINS;
    }


    public MethodButton(String label) {
        super(label);
        String v="";
        if (NAME_TO_PLUGIN_NAME.containsKey(label)) v = NAME_TO_PLUGIN_NAME.get(label);
        this.value = v;
        this.setLayoutX(posX);
        posX+=200;
    }

    public AnalyzerResult run () { //Execute le plugin Todo: amelioration de cette partie
        if (!PLUGINS.containsKey(this.value)) return null;
        var gitPath = FileSystems.getDefault().getPath("."); //cree une variable qui contient le chemin vers ce fichier
        var plugin = new HashMap<String, PluginConfig>();
        plugin.put(value,PLUGINS.get(value));
        var config = new Configuration(gitPath,plugin);
        return new Analyzer(config).computeResults();
    }

    public void toHtml(AnalyzerResult result) {
        System.out.println(result.toHTML());
    }



}
