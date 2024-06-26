/**
 * LEGENDE :
 * Pour repérer d'où viennent les méthodes, à qui sont les attributs etc., je procède comme ceci :
 * Class/X/nom/type, où :
 * -- Class est le nom de la classe
 * -- X appartient à {A:=attribut, M:=méthode}
 * -- le nom est le nom de l'attribut ou de la méthode
 * -- type est le type de l'attribut, pour une méthode il s'écrit ainsi : par1;par2;...;parN::typeDeRenvoie, s'il n'y a pas de paramètres il s'écrit ?::typeDeRenvoie
 * EXEMPLE :
 * Configuration/M/getGitPath/?::Path
 * */

package up.visulog.analyzer;

import up.visulog.config.Configuration;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Analyzer {

    private final Configuration config;
    private AnalyzerResult result;              /**attribut result qui est un AnalyzerResult (représenté par une liste d'analyzerPlugin.result : AnalyzerResult/A/subResults/List<AnalyzerPlugin.Result>)*/

    /**
     * Constructeur pour Analyser à partir d'une configuration
     * @param config La Configuration
     */
    public Analyzer(Configuration config) {
        this.config = config;
    }

    /**
     * Créé des plugins d'après Analyzer/A/config et les mets dans une liste de plugin $plugins, puis les fait tourner.
     * @return l'objet Analyzer ou les résultats sont stockés
     */
    public AnalyzerResult computeResults() {
        List<AnalyzerPlugin> plugins = new ArrayList<>();
        for (var pluginConfigEntry: config.getPluginConfigs().entrySet()) {  
            var pluginName = pluginConfigEntry.getKey();
            var pluginConfig = pluginConfigEntry.getValue();
            var plugin = makePlugin(pluginName); /**on créé un nouvel Objet de plugin, qui correspond au pluginName et on le mets dans une boite*/
            plugin.ifPresent(plugins::add);  /**on ajoute le plugin (s'il est bien dans la boite optional) à la liste plugins.*/
        }
        // run all the plugins
        ThreadGroup group = new ThreadGroup("plugins");
        for (var plugin : plugins) {
            new Thread(group, plugin).start();
        }
        /**ensuite on fait tourner tous les plugins qui sont dans la liste plugins.*/
        while (group.activeCount()>0) Thread.onSpinWait();
        // store the results together in an AnalyzerResult instance and return it
        return new AnalyzerResult(plugins.stream().map(AnalyzerPlugin::getResult).collect(Collectors.toList())); /**On créé une liste correspondant à l'image de plugins par CountCommitPerAuthorPlugin/M/getResult/?::Result, les Result contenant le resultat de l'analyse*/
    }

    /**
     * Crée un plugin
     * @param pluginName Nom du plugin
     * @return Le plugin, sinon un message d'erreur et une valeur vide
     */
    public Optional<AnalyzerPlugin> makePlugin(String pluginName) {
        /*Check if there's a plugin identified by the name given in the Configuration HashMap*/
        if(this.config.getPluginConfigs().containsKey(pluginName)){
            try {
                Constructor<?> classConstruct = findClassPlugins(pluginName).getConstructor(Configuration.class); // Get the constructor of the pluginClass using getConstructor() method
                return Optional.of((AnalyzerPlugin)classConstruct.newInstance(this.config));
            } catch (ReflectiveOperationException e){
                e.printStackTrace();
                System.out.println("Plugin not found.");
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Trouve la classe du plugin
     * @param pluginName
     * @return la classe Object pour le plugin
     * @throws ClassNotFoundException
     */
    public static Class <?> findClassPlugins(String pluginName) throws ClassNotFoundException {
        String plug=pluginName.substring(0,1).toUpperCase() + pluginName.substring(1);
        Class<?> c = Class.forName("up.visulog.analyzer.plugin."+plug);
        return c;
    }

    /**
     * Met dans une liste tous les plugins
     * @return La liste de plugins
     */
    public static ArrayList<String> listOfPlugins(){
        ArrayList<String> pluginsList = new ArrayList<>();
        try{
            File dir = new File("../analyzer/src/main/java/up/visulog/analyzer/plugin");
            File [] files = dir.listFiles();
            for(File classes : files){
                if(!classes.getName().equals("Research.java"))
                    pluginsList.add(classes.getName().replace(".java",""));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return pluginsList;
    }
}
