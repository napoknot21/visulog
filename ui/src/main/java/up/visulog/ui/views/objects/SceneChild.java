package up.visulog.ui.views.objects;

import up.visulog.ui.views.scenes.VisulogScene;

@FunctionalInterface
public interface SceneChild {

    /**
     * Initialise les variable necessaire au bon fonctionnement
     *
     * @param scene represente la scene principale de la gui
     */
    void setup(VisulogScene scene);

}
