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
    private String value = "";
    public MethodRadioButton(String label) {
        super(label);
    }    protected static HashMap<String, String> RADIO_BUTTON_NAME = initializeRadioButtonName();
    //Map (nom du filtre -> nom du plugin)

    private static HashMap<String, String> initializeRadioButtonName() { //initialise la map
        RADIO_BUTTON_NAME = new HashMap<>();
        RADIO_BUTTON_NAME.put("Par jour", "PerDay");
        //Todo: a remplir selon les filtres

        return RADIO_BUTTON_NAME;
    }

    @Override
    public AnalyzerResult run() { //Lance le plugin relier au filtre
        if (!PLUGINS.containsKey(this.value)) return null;
        var gitPath = FileSystems.getDefault().getPath("."); //cree une variable qui contient le chemin vers ce fichier
        var plugin = new HashMap<String, PluginConfig>();
        plugin.put(value, PLUGINS.get(value));
        var config = new Configuration(gitPath, plugin);
        return new Analyzer(config).computeResults();
    }

    @Override
    public String toHtml(AnalyzerResult result) { //Genere le html
        if (result == null) return "Empty !";
        System.out.println(result.toHTML());
        return result.toHTML();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String plugin) {
        System.out.println(plugin);
        String v = "";
        String name = "";
        if (RADIO_BUTTON_NAME.containsKey(this.getText())) name = RADIO_BUTTON_NAME.get(this.getText());
        plugin += name;
        if (NAME_TO_PLUGIN_NAME.containsKey(plugin)) v = NAME_TO_PLUGIN_NAME.get(plugin);
        this.value = v;
    }


}
