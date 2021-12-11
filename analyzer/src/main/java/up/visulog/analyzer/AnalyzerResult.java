package up.visulog.analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyzerResult { //classe qui contient les resultats obtenus via Analyzer sous forme de liste
    public List<AnalyzerPlugin.Result> getSubResults() {
        return subResults;
    } //renvoie la liste des resultats

    private final List<AnalyzerPlugin.Result> subResults; //liste des resultats

    public AnalyzerResult(List<AnalyzerPlugin.Result> subResults) { //constructeur de la classe
        this.subResults = subResults;
    }

    public Map <String,Integer> toMap () { //Renvoi les resultats en tant que map
        return subResults.stream().map(AnalyzerPlugin.Result::getResultAsMap).reduce(new HashMap<>(), (map1, map2) -> {
            for (String key : map2.keySet()) {
                int value2 = map2.getOrDefault(key,0);
                int value1 = map1.getOrDefault(key, 0);
                map1.put(key,value1+value2);
            }
            return map1;
        });
    }

    @Override
    public String toString() { //renvoie les resultats en tant que String
         return subResults.stream().map(AnalyzerPlugin.Result::getResultAsString).reduce("", (acc, cur) -> acc + "\n" + cur);

    }

    public String toHTML() { //renvoie les resultats dans un format HTML
        String head = "<head></head>";
        String body =  "<body>"+subResults.stream().map(AnalyzerPlugin.Result::getResultAsHtmlDiv).reduce("", (acc, cur) -> acc + cur) + "</body>";
        return "<html>" + head + body + "</html>";
    }

    /*Informations un peu plus détaillées sur ce que renvoient les méthodes toString() et toHTML():
    subResults : résultats obtenus via Analyzer sous forme de liste
    stream() : renvoie subResults en tant que sequential stream (=flux sequentiel)
    map() : applique la fontion entre () à chaque element du stream (ici la fonction ecrit chaque element au format String ou HTML)
    reduce() : combine les resultats pour ne renvoyer qu'une seule ligne à la fin
     */
}
