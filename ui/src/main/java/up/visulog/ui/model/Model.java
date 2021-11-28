package up.visulog.ui.model;

import up.visulog.analyzer.AnalyzerResult;
import up.visulog.config.PluginConfig;

import java.util.HashMap;

public class Model implements MapRelations {

    private AnalyzerResult result;


    public void update(AnalyzerResult result) {
        this.result = result;

    }

    public String toHtml() { // retourne le resultats sous html
        if (result == null) return "";
        System.out.println(result.toHTML());
        return result.toHTML();
    }

    public AnalyzerResult getResult() {
        return result;
    }

    public HashMap<String, PluginConfig> getPLUGINS() {
        return PLUGINS;
    }

    public HashMap<String, String> getBUTTON_NAME_TO_PLUGIN_NAME() {
        return BUTTON_NAME_TO_PLUGIN_NAME;
    }

    public HashMap<String, String> getNAME_TO_PLUGIN_NAME() {
        return NAME_TO_PLUGIN_NAME;
    }

    public HashMap<String, String> getRADIO_BUTTON_NAME() {
        return RADIO_BUTTON_NAME;
    }
}
