package up.visulog.ui.controller;

import javafx.scene.control.Button;
import up.visulog.analyzer.Analyzer;
import up.visulog.analyzer.AnalyzerResult;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;

import java.nio.file.FileSystems;
import java.util.HashMap;

public class MethodButton extends Button
        implements UIController { //Classe Button permettant de lancer les plugins a partir des boutons

    static int posX = 0;
    private final String value;

    public MethodButton(String label) {
        super(label);
        String v = "";
        if (NAME_TO_PLUGIN_NAME.containsKey(label)) v = NAME_TO_PLUGIN_NAME.get(label);
        this.value = v;
        this.setLayoutX(posX);
        posX += 200;
    }

    @Override
    public AnalyzerResult run() {
        if (!PLUGINS.containsKey(this.value)) return null;
        var gitPath = FileSystems.getDefault().getPath("."); //cree une variable qui contient le chemin vers ce fichier
        var plugin = new HashMap<String, PluginConfig>();
        plugin.put(value, PLUGINS.get(value));
        var config = new Configuration(gitPath, plugin);
        return new Analyzer(config).computeResults();
    }

    public String toHtml(AnalyzerResult result) {
        System.out.println(result.toHTML());
        return result.toHTML();
    }

    public String getValue() {
        return value;
    }
}
