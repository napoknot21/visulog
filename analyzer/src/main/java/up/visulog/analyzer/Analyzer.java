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
import up.visulog.config.PluginConfig;

import java.lang.reflect.Constructor;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Analyzer {
    private final Configuration config;         /**un objet Analyzer a un attribut config qui est une Configuration (représentée par : Configuration/A/gitPath/Path et Configuration/A/plugins/Map<String, PluginConfig> )*/

    private AnalyzerResult result;              /**il a aussi un attribut result qui est un AnalyzerResult (représenté par une liste d'analyzerPlugin.result : AnalyzerResult/A/subResults/List<AnalyzerPlugin.Result>)*/

    public Analyzer(Configuration config) {     /**Constructeur à partir d'une Configuration*/
        this.config = config;
    }

    public AnalyzerResult computeResults() {    /**méthode d'objet computeResult() qui créé des plugins d'après Analyzer/A/config et les mets dans une liste de plugin $plugins, puis les fait tourner.*/
        List<AnalyzerPlugin> plugins = new ArrayList<>();
        for (var pluginConfigEntry: config.getPluginConfigs().entrySet()) {  
            var pluginName = pluginConfigEntry.getKey();
            var pluginConfig = pluginConfigEntry.getValue();
            var plugin = makePlugin(pluginName); /**on créé un nouvel Objet de plugin, qui correspond au pluginName et on le mets dans une boite*/
            plugin.ifPresent(plugins::add);  /**on ajoute le plugin (s'il est bien dans la boite optional) à la liste plugins.*/
        }
        // run all the plugins
        // TODO#1: try running them in parallel
        for (var plugin: plugins) plugin.run(); /**ensuite on fait tourner tous les plugins qui sont dans la liste plugins.*/

        // store the results together in an AnalyzerResult instance and return it
        return new AnalyzerResult(plugins.stream().map(AnalyzerPlugin::getResult).collect(Collectors.toList())); /**On créé une liste correspondant à l'image de plugins par CountCommitPerAuthorPlugin/M/getResult/?::Result, les Result contenant le resultat de l'analyse*/
    }

    // TODO#2: find a way so that the list of plugins is not hardcoded in this factory


    protected Optional<AnalyzerPlugin> makePlugin(String pluginName) {
        /*Check if there's a plugin identified by the name given in the Configuration HashMap*/
        if(this.config.getPluginConfigs().containsKey(pluginName)){
            try{
                @SuppressWarnings("unchecked")
                /* Save the plugin Class*/
                Class<?> c= (Class<AnalyzerPlugin>)Class.forName(pluginName+"Plugin");
                Constructor<?> [] pluginConstructor = c.getConstructors();
                return Optional.of((AnalyzerPlugin)pluginConstructor[0].newInstance(this.config));


            }catch (Exception e){
                return Optional.empty();
            }
        }
        return Optional.empty();

    }


}
