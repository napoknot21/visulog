package up.visulog.ui.views.objects;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import up.visulog.ui.model.Model;

public class MethodRadioButton extends RadioButton
        implements PluginButtons {
    private String value = "";
    private static final ToggleGroup group = new ToggleGroup();

    public MethodRadioButton(String label) {
        super(label);
        setToggleGroup(group);
    }


    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String plugin) {
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
