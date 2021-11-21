package up.visulog.ui.controller;

import javafx.scene.control.RadioButton;
import up.visulog.analyzer.Analyzer;
import up.visulog.analyzer.AnalyzerResult;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;

import java.nio.file.FileSystems;
import java.util.HashMap;

public class MethodRadioButton extends RadioButton
        implements UIController {
    private final String value;

    public MethodRadioButton(String label, String plugin) {
        super(label);
        String v = "";
        String name = "";
        if (RADIO_BUTTON_NAME.containsKey(label)) name = RADIO_BUTTON_NAME.get(label);
        plugin += name;
        if (NAME_TO_PLUGIN_NAME.containsKey(plugin)) v = NAME_TO_PLUGIN_NAME.get(plugin);
        this.value = v;
    }

    private static HashMap<String, String> initializeRadioButtonName() {
        RADIO_BUTTON_NAME = new HashMap<>();
        RADIO_BUTTON_NAME.put("Par jour", " PerDay");


        return RADIO_BUTTON_NAME;
    }

    @Override
    public AnalyzerResult run() {
        if (!PLUGINS.containsKey(this.value)) return null;
        var gitPath = FileSystems.getDefault().getPath("."); //cree une variable qui contient le chemin vers ce fichier
        var plugin = new HashMap<String, PluginConfig>();
        plugin.put(value, PLUGINS.get(value));
        var config = new Configuration(gitPath, plugin);
        return new Analyzer(config).computeResults();
    }    protected static HashMap<String, String> RADIO_BUTTON_NAME = initializeRadioButtonName();

    @Override
    public String toHtml(AnalyzerResult result) {
        if (result == null) return "Empty !";
        System.out.println(result.toHTML());
        return result.toHTML();
    }




}
