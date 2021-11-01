package up.visulog.config;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Configuration implements Serializable { //Classe pour associer un chemin à un (ou plusieurs) Plugin(s) configurés d'une certaine manière

    private final Path gitPath; //Chemin pour le plugin
    private final Map<String, PluginConfig> plugins; //Le(s) plugin(s) en question, organisé(s) dans une Map (associé à une chaîne de caractères)
    private static final String configFileName = "config.txt"; //Nom de fichier où les configurations seront stockées

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

    public static Configuration loadConfigFile(Path Filepath)  {
        try{
            File file = new File(String.valueOf(Filepath)+configFileName);
            /*Procède à la lecture du fichier afin de récupérer les données nécéssaire à la création de configurations à l'intérieur*/
            /*Ce procédé est ce qu'on appelle 'Deserialization' */
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

            Configuration res = (Configuration)ois.readObject();
            System.out.println("Configuration file loaded successfully.");
            ois.close();
            return res;
        } catch (IOException |ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("The file couldn't be loaded.");
            return null;
        }
    }

    //Fichier où on va sauvegarder les configurations
    private File getConfigFile(){
        File configFile = new File(gitPath + configFileName);
        //Si le fichier existe il retourne le dit fichier sinon on crée un nouveau fichier
        //qui contiendra les configurations
        try{
            configFile.createNewFile();
        }catch(IOException e){
            System.out.println("The configuration file couldn't be created.");
        }
        return configFile;
    }
}
