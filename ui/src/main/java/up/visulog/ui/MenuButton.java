package up.visulog.ui;

import java.util.HashMap;

public class MenuButton extends MethodButton{ // Represente les boutons du menu
    private static double nextPosY = 0;

    public  MenuButton(String label) {
        super(label);
        this.setLayoutY(nextPosY);
        nextPosY =  this.getLayoutY() + 20;
    }
}
