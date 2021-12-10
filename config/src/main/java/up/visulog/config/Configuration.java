package up.visulog.config;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration implements Serializable { //Classe pour associer un chemin à un (ou plusieurs) Plugin(s) configurés d'une certaine manière

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

    public static Configuration loadConfigFile(String FilePath)  {
        try{
            File file = new File(FilePath);
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
