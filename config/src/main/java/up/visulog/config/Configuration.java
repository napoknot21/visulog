package up.visulog.config;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration implements Serializable { //Classe pour associer un chemin à un (ou plusieurs) plugin(s) configurés d'une certaine manière

    private final Path gitPath; //Chemin pour le plugin
    private final Map<String, PluginConfig> plugins; //Le(s) plugin(s) en question, organisé(s) dans une Map (associé à une chaîne de caractères)

    public Configuration(Path gitPath, Map<String, PluginConfig> plugins) {
        this.gitPath = gitPath;
        this.plugins = Map.copyOf(plugins);
    }
    
    /**
     * @return le chemin du plugin
     */
    public Path getGitPath() { //Un simple getter qui permet de récupérer le chemin associé au plugin
        return gitPath;
    }

    /**
     * @return les plugins dans un Map
     */
    public Map<String, PluginConfig> getPluginConfigs() { //Pareil pour récupérer les plugins configurés
        return plugins;
    }

    /**
     * @param key représente la cle de l'objet dans la liste des plugin
     * @return la liste des plugins qui correspond à key
     */
    public PluginConfig getPluginConfig(String key){
        return plugins.get(key);
    }

    /**
     * Crée un fichier modifié pValue à partir du contenu de pName_file si le fichier pValue n'existe pas et affiche son erreur
     *
     * @param pValue représente le nom du fichier crée.
     * @param pName_file représente le contenue du fichier crée.
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
