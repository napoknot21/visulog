package up.visulog.config;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration implements Serializable { //Classe pour associer un chemin à un (ou plusieurs) Plugin(s) configurés d'une certaine manière

    private final Path gitPath;
    private final Map<String, PluginConfig> plugins;

    /**
     * constructeur de Configuration
     * @param gitPath le chemin associé au plugin
     * @param plugins Liste(s) de plugin(s) dans une Map (associé à une chaîne de caractères)
     */
    public Configuration(Path gitPath, Map<String, PluginConfig> plugins) {
        this.gitPath = gitPath;
        this.plugins = Map.copyOf(plugins);
    }

    /**
     * Getter pour le chemin associé du plugin
     * @return le chemin associé au plugin de la configuration
     */
    public Path getGitPath() {
        return gitPath;
    }

    /**
     * Getter pour la liste (map) des plugins de la Configuration
     * @return la liste (map) des plugins de la Configuration
     */
    public Map<String, PluginConfig> getPluginConfigs() {
        return plugins;
    }

    /**
     * Getter pour la valeur associée à la clé passée en paramètre
     * @param key la clé de l'objet dans la liste des plugins
     * @return le PluginConfig associé à la clé
     */
    public PluginConfig getPluginConfig(String key){
        return plugins.get(key);
    }

    /**
     * Crée un fichier avec son le contenu passé en paramètre, sinon il affiche un message d'erreur
     * @param pValue Le nom du fichier créé
     * @param pName_file Le contenu du fichier créé
     */
    public static void createModifFile (String pValue, String pName_file) {
        try {
            File f = new File("./Files/"+pValue+".txt");

            if (f.createNewFile()) {
                FileWriter fw = new FileWriter(f);
                fw.write(pName_file);
                fw.close();
                System.out.println("The file has been created.");

            } else {
                System.out.println("The file already exists.");
            }
        } catch (IOException e) {
            System.out.println("OOPS! An error occurred while creating the file. Please try again.");
            e.printStackTrace();
        }
    }

}
