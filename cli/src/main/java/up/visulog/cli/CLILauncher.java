package up.visulog.cli;

import up.visulog.analyzer.Analyzer;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;
import up.visulog.ui.VisulogGUI;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;

public class CLILauncher {

    public static void main(String[] args) {
        if (args.length==0) return;
        var config = makeConfigFromCommandLineArgs(args);  //creation de la configuration en fonction des arguments choisis (voir fonction ci dessous)
        if (config.isPresent()) { //verifie qu'une configuration a bien ete cree
            var analyzer = new Analyzer(config.get()); //cree une variable analyzer qui contient un Analyzer cree à partir de la config reçue
            var results = analyzer.computeResults(); //recupere les resultats de l'analyzer (voir Analyzer.java)
            System.out.println(results.toHTML()); //affiche ces resultats au format HTML
        } else {
            System.out.println("Wrong command...");
            displayHelpAndExit(); //voir fonction ci dessous
        }
    }

    static Optional<Configuration> makeConfigFromCommandLineArgs(String[] args) { //reçoit les arguments passés en ligne de commande
        var gitPath = FileSystems.getDefault().getPath("."); //cree une variable qui contient le chemin vers ce fichier
        var plugins = new HashMap<String, PluginConfig>(); //cree une hashmap avec pour cles des Strings et pour valeur des "PluginConfig" (-> à definir dans PluginConfig.java)
        /*Une hashmap est une sorte de liste qui associe à chaque valeur une clé qui permet de la retrouver facilement (bien plus pratique que les listes vues en L1) */
        for (var arg : args) {
            if (arg.startsWith("--")) { //verifie que les arguments sont au format "--nomArg=valArg"
                String[] parts = arg.split("="); //separe chaque argument en 2: le nom de l'argument (ex: "--addPlugin") et sa valeur ("ex: countCommits"), et les met dans un tableau
                if (parts.length != 2) {
                    return Optional.empty(); //renvoie une valeur vide s'il manque le nom ou la valeur de l'argument
                }
                else {
                    String pName = parts[0];
                    String pValue = parts[1];
                    switch (pName) {
                        //sépare les différents cas de figure en fonction du nom de l'argument
                        case "--displayHelp":
                            /*à gérer dans l'interface :
                            fixer la commande de displayHelp telle que l'argument soit '--displayHelp=help'
                            ou autre du moment qu'il y ait  deux mots non vides séparés de '='
                             */
                            displayHelpAndExit(); break;

                        case "--addPlugin":
                            // TODO#1: parse argument and make an instance of PluginConfig

                            // Let's just trivially do this, before the TODO is fixed:

                            if (pValue.equals("countCommits"))
                                plugins.put("countCommits", new PluginConfig() { });//si l'argument a pour valeur "countCommits", crée un

                            else if (pValue.equals("countMergeCommits"))
                                plugins.put("countMergeCommits", new PluginConfig(){});
                            else
                                return Optional.empty();

                            break;
                        case "--loadConfigFile":
                            // TODO#2 (load options from a file)
                                if(pValue.length()==0)
                                    displayHelpAndExit();

                                else if (check_directory(pValue)){
                                    Configuration res = Configuration.loadConfigFile(pValue);
                                    return Optional.ofNullable(res);
                                }
                                return Optional.empty();

                        case "--justSaveConfigFile":
                            // TODO#3 (save command line options to a file instead of running the analysis)
                            if (pValue.equals("")) displayHelpAndExit();
                            else {
                                String pName_file = "--addPlugin=";

                                switch (pValue) {
                                    case "countCommits":
                                        pName_file += "countCommits";
                                        break;
                                    /*Il faudra completer ce switch avec les autres cas*/
                                    default:
                                        pName_file = "";
                                        break;
                                }

                                if (!pName_file.equals("")) {

                                    File dir = new File("./Files");
                                    if (!dir.isDirectory()) dir.mkdir();

                                    createModifFile(pValue,pName_file);

                                } else {
                                    displayHelpAndExit();
                                }
                            }
                            break;
                        default:
                            return Optional.empty(); //renvoie une valeur vide si le nom de l'argument n'est pas valide
                    }
                }
            } else {
                gitPath = FileSystems.getDefault().getPath(arg); //si l'argument n'est pas au format "--nomArg=valeurArg", donne à la variable gitPath le chemin donne par l'argument
            }
        }
        return Optional.of(new Configuration(gitPath, plugins)); //renvoie une configuration si c'est possible (si un plugin a bien ete defini)
    }

    private static boolean check_directory(String path){
        File directory = new File(path);
        return (directory.exists() && directory.isDirectory());
    }

    public static void createModifFile (String pValue, String pName_file) {
        try {
            File f = new File("./Files/"+pValue+".txt");

            if (f.createNewFile()) {
                FileWriter fw = new FileWriter(f);
                fw.write(pName_file);
                fw.close();
                System.out.println("Le fichier a été crée");

            } else {
                System.out.println("Le fichier existe déjà");
            }
        } catch (IOException e) {
            System.out.println("Il s'est produit une erreur. Essayez de nouveau");
            e.printStackTrace();
        }
    }

    private static void displayHelpAndExit() { //liste les noms d'arguments valables (et leurs valeurs?) et arrête le programme

        //En théorie, cela devrait afficher les arguments et leurs valeurs
        //Il va falloir finir le switch precedent pour completer toutes les valeurs des arguments dans cette fonction
        //Je propose ce "format" :
        System.out.println("Try it again with this format: '. --[NAME_ARGUMENT]=[ARG_VALUE]'");
        System.out.println("Some options...");
        System.out.println(". --addPlugin="); //nom de l'argument
        System.out.println("        countCommits"); //Deux tabulations pour les valuers possibles de l'argument courant
        System.out.println(". --loadConfigFile=");
        System.out.println(". --justSaveConfigFile=");
        //TODO#4: print the list of options and their syntax
        System.exit(0);
    }
}
