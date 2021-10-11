package up.visulog.cli;

import up.visulog.analyzer.Analyzer;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;

import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Optional;

public class CLILauncher {

    public static void main(String[] args) {
        var config = makeConfigFromCommandLineArgs(args);  //création de la configuration en fonction des arguments choisis (voir fonction ci dessous)
        if (config.isPresent()) {
            var analyzer = new Analyzer(config.get());
            var results = analyzer.computeResults();
            System.out.println(results.toHTML());
        } else displayHelpAndExit();
    }

    static Optional<Configuration> makeConfigFromCommandLineArgs(String[] args) { //reçoit les arguments passés en ligne de commande
        var gitPath = FileSystems.getDefault().getPath("."); //cree une variable qui contient le chemin vers ce fichier
        var plugins = new HashMap<String, PluginConfig>(); //cree une hashmap avec pour cles des Strings et pour valeur des "PluginConfig" (-> à definir dans PluginConfig.java)
        /*Une hashmap est une sorte de liste qui associe à chaque valeur une clé qui permet de la retrouver facilement (bien plus pratique que les listes vues en L1) */
        for (var arg : args) {
            if (arg.startsWith("--")) { //verifie que les arguments sont au format "--nomArg=valArg"
                String[] parts = arg.split("="); //separe chaque argument en 2: le nom de l'argument (ex: "--addPlugin") et sa valeur ("ex: countCommits"), et les met dans un tableau
                if (parts.length != 2) return Optional.empty(); //renvoie une valeur vide s'il manque le nom ou la valeur de l'argument
                else {
                    String pName = parts[0];
                    String pValue = parts[1];
                    switch (pName) { //sépare les différents cas de figure en fonction du nom de l'argument
                        case "--addPlugin":
                            // TODO#1: parse argument and make an instance of PluginConfig

                            // Let's just trivially do this, before the TODO is fixed:

                            if (pValue.equals("countCommits")) plugins.put("countCommits", new PluginConfig() { //si l'argument a pour valeur "countCommits", crée un
                            });

                            break;
                        case "--loadConfigFile":
                            // TODO#2 (load options from a file)
                            break;
                        case "--justSaveConfigFile":
                            // TODO#3 (save command line options to a file instead of running the analysis)
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

    private static void displayHelpAndExit() { //liste les noms d'arguments valables (et leurs valeurs?) et arrete le programme
        System.out.println("Wrong command...");
        //TODO#4: print the list of options and their syntax
        System.exit(0);
    }
}
