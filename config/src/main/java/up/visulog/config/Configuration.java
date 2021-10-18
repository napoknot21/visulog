package up.visulog.config;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Configuration { //Classe pour associer un chemin à un (ou plusieurs) Plugin(s) configurés d'une certaine manière

    private final Path gitPath; //Chemin pour le plugin
    private final Map<String, PluginConfig> plugins; //Le(s) plugin(s) en question, organisé(s) dans une Map (associé à une chaîne de caractères)

    public Configuration(Path gitPath, Map<String, PluginConfig> plugins) {
        this.gitPath = gitPath;
        this.plugins = Map.copyOf(plugins);
    }

    public Path getGitPath() { //Un simple getter qui permet de récupérer le chemin associé au plugin
        return gitPath;
    }

    public Map<String, PluginConfig> getPluginConfigs() { //Pareil pour récupérer les plugins configurés
        return plugins;
    }
}
