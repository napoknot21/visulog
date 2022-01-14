package up.visulog.ui.views.objects;

import up.visulog.ui.views.scenes.VisulogScene;

@FunctionalInterface
public interface SceneChild {

    void setup(VisulogScene scene);

}
