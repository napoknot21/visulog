package up.visulog.cli;

import up.visulog.analyzer.Analyzer;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;
import up.visulog.ui.VisulogLauncher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.nio.file.OpenOption;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class CLILauncher {

    public static void main(String[] args) {
        if (args.length == 0 ) {
            VisulogLauncher.run(args);
            return;
        }
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

        /*On admet qu'on arrive à la fin des arguments d'une commande que lorsqu'une nouvelle commande commence*/
        ArrayList<String> input = inputFiltering(args);

        for (var arg : input) {
            if (arg == null) displayHelpAndExit();
            if (arg.startsWith("--")) { //verifie que les arguments sont au format "--nomArg=valArg"
                String[] parts = arg.split("="); //separe chaque argument en 2: le nom de l'argument (ex: "--addPlugin") et sa valeur ("ex: countCommits"), et les met dans un tableau
                if (parts.length != 2) {
                    if ((parts.length > 0) && (parts[0].equals("--allPlugin"))) {
                        plugins = getAllPlugin();
                        return Optional.of(new Configuration(gitPath, plugins));
                    }
                    return Optional.empty(); //renvoie une valeur vide s'il manque le nom ou la valeur de l'argument
                } else {
                    String pName = parts[0];
                    String pValue = parts[1];

                    switch (pName) {
                        //sépare les différents cas de figure en fonction du nom de l'argument
                        case "--h":
                        case "--HELP":
                        case "--Help":
                        case "--help":
                        case "--displayHelp":

                            displayHelpAndExit();
                            break;

                        case "--research":
                                researchConfig(plugins,pValue);
                            break;

                        case "--addPlugin":
                            addPlugin(pValue, plugins);
                            break;

                        case "--load":
                        case "--loadConfigFile":
                            return loadConfigFile(pValue);

                        case "--save":
                        case "--justSaveConfigFile":
                            justSaveConfigFile(pValue);
                            break;

                        default:
                            System.out.println("Unknown plugin");
                            return Optional.empty();
                        // renvoie une valeur vide si le nom de l'argument n'est pas valide
                    }
                }
            } else {
                gitPath = FileSystems.getDefault().getPath(arg); //si l'argument n'est pas au format "--nomArg=valeurArg", donne à la variable gitPath le chemin donne par l'argument
            }
        }
        if (plugins.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Configuration(gitPath, plugins)); //renvoie une configuration si c'est possible (si un plugin a bien ete defini)
    }


    public static ArrayList<String> inputFiltering(String[]args){
        /*Pour traiter le cas où l'utilisateur pourra insèrer plusieures commandes à la fois
        * cas limite : l'argument pValue peut contenir des espaces donc on traite ce cas ici
        * pour que chaque argument soit délimiter par '--' qui se trouve au début de chaque commande
        * */
        ArrayList<String> input = new ArrayList<>();
        int pos = 0 ;

        while (pos<args.length){
            String str = "";
            do {
                if (pos==0 || args[pos].startsWith("--") && !args[pos].startsWith("--research")){
                    str += args[pos++];
                }
                else{
                    str +=args[pos++]+" ";
                }
            }while (pos < args.length && !args[pos].startsWith("--"));

            input.add(str);
        }
        return input;
    }

    private static void researchConfig(HashMap<String, PluginConfig> plugins, String pValue){
        if(pValue.length()==0)  displayHelpAndExit();
        ArrayList<String> keyWords = new ArrayList<>();
        try(Scanner sc = new Scanner(pValue)) {
            while (sc.hasNext()) {
                keyWords.add(sc.next());
            }
        }
        HashMap <String, ArrayList<String>> pluginConfig = new HashMap<>();
        pluginConfig.put("keyWords",keyWords);
        plugins.put("research", new PluginConfig(pluginConfig));
    }

    private static HashMap<String, PluginConfig> getAllPlugin() {
        HashMap<String, PluginConfig> plugins = new HashMap<>();
        Analyzer.listOfPlugins("..").forEach(s -> plugins.put(s, new PluginConfig()));
        return plugins;
    }

    private static void check_directory() {
        File directory = new File("./Files");
        if (!directory.isDirectory()) {
            if (!directory.mkdir()) {
                System.out.println("Files directory cannot be created");
                displayHelpAndExit();
            }
        }
    }

    private static void justSaveConfigFile(String pValue) {
        if (pValue.equals("")) displayHelpAndExit();
        if (pValue.equals("--allPlugin")) {
           check_directory();
           Configuration.createModifFile(pValue,pValue);
           return;
        }
            String pName_file = "--addPlugin=";
            try {
                Analyzer.findClassPlugins(pValue);
                pName_file += pValue;
            } catch (ClassNotFoundException e) {
                System.out.println("Unknown plugin");
                displayHelpAndExit();
            }
        check_directory();
        Configuration.createModifFile(pValue, pName_file);
    }

    private static Optional<Configuration> loadConfigFile(String pValue) {
        if (pValue.length() == 0) {
            displayHelpAndExit();

        } else {
            File dir = new File("./Files");
            if (!dir.isDirectory()) {
                System.out.println("Files: no such directory.");
                displayHelpAndExit();
            }
            try {
                File f = new File("./Files/" + pValue + ".txt");
                if (!f.isFile()) {
                    System.out.println("The configFile doesn't exist.");
                    displayHelpAndExit();
                }
                BufferedReader br = new BufferedReader(new FileReader(f));
                return makeConfigFromCommandLineArgs(new String[]{br.readLine()});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    private static void addPlugin(String pValue, HashMap<String, PluginConfig> plugins) {
        try {
            Analyzer.findClassPlugins(pValue);
            plugins.put(pValue, new PluginConfig());
        } catch (ClassNotFoundException e) {
            displayHelpAndExit();
        }
    }

    public static void displayHelpAndExit() { //liste les noms d'arguments valables (et leurs valeurs?) et arrête le programme

        System.out.print("---Manual---" +
                "\nTo run the software through gradle, you need to pass the program arguments behind '--args=' ." +
                "\nFor instance: ./gradlew run --args='. --addPlugin=CountCommits' : " +
                "Will count the commits of each author in the current branch of the git repository present in the current folder (\".\")." +
                "\n\nValid arguments: " +
                "\n\t\t --addPlugin=");
        for (String plug : Analyzer.listOfPlugins("..")) {
            System.out.print("\n\t\t\t" + plug);
        }
        System.out.print("\n\t\t --research=[keyWord] : to load all commits related to the keyWord" );
        System.out.print("\n\t\t --loadConfigFile=[pluginName*] or --load=[pluginName*]: to load an existing plugin in the configuration" +
                "\n\t\t --justSaveConfigFile=[pluginName*] or --save=[pluginName*]: to save a plugin in the configuration" +
                "\n\n *from the list of plugins above");
        System.exit(0);
    }


}
