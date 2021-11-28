package up.visulog.ui.views;

import javafx.scene.control.Button;
import up.visulog.analyzer.AnalyzerResult;
import up.visulog.ui.model.Model;

public class MethodButton extends Button
        implements VisulogButtons, SceneChild { //Classe Button permettant de lancer les plugins a partir des boutons

    static int posX = 0;

    private String value;
    private Model model;

    public MethodButton(String label) {
        super(label);
        String v = "";
        if (Model.BUTTON_NAME_TO_PLUGIN_NAME.containsKey(label)) v = Model.BUTTON_NAME_TO_PLUGIN_NAME.get(label);
        this.value = v;
        this.setLayoutX(posX);
        posX += 200;
    }

    public String toHtml(AnalyzerResult result) { // retourne le resultats sous html
        if (result == null) return "";
        System.out.println(result.toHTML());
        return result.toHTML();
    }


    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String plugin) {
        this.value = plugin;
    }

    @Override
    public String getPlugin() {
        return value;
    }

    @Override
    public void setup(VisulogScene scene) {
        model = scene.getModel();
    }
}
