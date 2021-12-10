package up.visulog.cli;

import up.visulog.analyzer.Analyzer;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;


import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import java.io.*;

import java.nio.file.FileSystems;
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
            System.out.println("[UNKNOWN ARGUMENTS]");
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

                            try{
                                if (Analyzer.findClassPlugins(pValue)!=null) plugins.put(pValue, new PluginConfig());
                            }catch(ClassNotFoundException e){
                                return Optional.empty();
                            }

                            break;
                        case "--loadConfigFile":
                                if(pValue.length()==0){
                                    displayHelpAndExit();

                                } else{
                                    File dir = new File("./Files");
                                    if(!dir.isDirectory()) {
                                        System.out.println("Files: no such directory.");
                                        displayHelpAndExit();
                                    }
                                    try{
                                        File f = new File("./Files/"+pValue+".txt");
                                        if(!f.isFile()) {
                                            System.out.println("The configFile doesn't exist.");
                                            displayHelpAndExit();
                                        }
                                        BufferedReader br = new BufferedReader(new FileReader(f));
                                        return makeConfigFromCommandLineArgs(new String[]{br.readLine()});
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                        case "--justSaveConfigFile":
                            if (pValue.equals("")) displayHelpAndExit();
                            else {
                                String pName_file = "--addPlugin=";
                                try{
                                    if (Analyzer.findClassPlugins(pValue) !=null){
                                        pName_file += pValue;
                                    }
                                }catch (ClassNotFoundException e){
                                    System.out.println("PLugin not valid.");
                                    displayHelpAndExit();
                                }

                                if (!pName_file.equals("")) {

                                    File dir = new File("./Files");
                                    if (!dir.isDirectory()) dir.mkdir();

                                    Configuration.createModifFile(pValue,pName_file);

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
        return (directory.exists());
    }


    private static void displayHelpAndExit() { //liste les noms d'arguments valables (et leurs valeurs?) et arrête le programme

        System.out.print("---Manual---" +
                "\nTo run the software through gradle, you need to pass the program arguments behind '--args=' ." +
                "\nFor instance: ./gradlew run --args='. --addPlugin=CountCommits' : " +
                "Will count the commits of each author in the current branch of the git repository present in the current folder (\".\")." +
                "\n\nValid arguments: " +
                "\n\t\t --addPlugin=");
        for(String plugins : Analyzer.listOfPlugins("..")){
            System.out.print("\n\t\t\t"+plugins);
        }
        System.out.print("\n\t\t --loadConfigFile=[pluginName*] : to load an existing plugin in the configuration" +
                "\n\t\t --justSaveConfigFile=[pluginName*] : to save a plugin in the configuration" +
                "\n\n *from the list of plugins above" );
        System.exit(0);
    }


}
