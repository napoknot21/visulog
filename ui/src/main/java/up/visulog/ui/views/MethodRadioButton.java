package up.visulog.ui.views;

import javafx.scene.control.RadioButton;
import up.visulog.ui.model.Model;

public class MethodRadioButton extends RadioButton
        implements VisulogButtons {
    private String value = "";

    public MethodRadioButton(String label) {
        super(label);
    }


    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String plugin) {
        System.out.println(plugin);
        String v = "";
        String name = "";
        if (Model.RADIO_BUTTON_NAME.containsKey(this.getText())) name = Model.RADIO_BUTTON_NAME.get(this.getText());
        plugin += name;
        if (Model.NAME_TO_PLUGIN_NAME.containsKey(plugin)) v = Model.NAME_TO_PLUGIN_NAME.get(plugin);
        this.value = v;
    }

    @Override
    public String getPlugin() {
        return value;
    }


}
