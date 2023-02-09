package up.visulog.analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AnalyzerResult { //classe qui contient les résultats obtenus via Analyzer sous forme de liste

    private final List<AnalyzerPlugin.Result> subResults;

    /**
     * Constructeur de AnalyzerResult à partir d'une liste de résultats
     * @param subResults La liste de Résultats
     */
    public AnalyzerResult(List<AnalyzerPlugin.Result> subResults) {
        this.subResults = subResults;
    }

    /**
     * Getter pour la liste de résultats d'AnalyzerResult
     * @return La liste de résultats
     */
    public List<AnalyzerPlugin.Result> getSubResults() {
        return subResults;
    }

    /**
     * @return Les résultats sous forme d'une Map
     */
    public Map<String, Object> toMap() {
        return subResults.stream().map(AnalyzerPlugin.Result::getResultAsMap).reduce(new HashMap<>(), (map1, map2) -> {
            if (map2 != null) {
                map2.forEach((key, obj) -> selector(obj, key, map1));
            }
            return map1;
        });
    }

    /**
     * Choisis le traitement approprie en fonction du type de C
     * @param obj  Représente l'objet à travailler
     * @param key  Représente la cle de l'objet dans la map
     * @param map1 est l'accumulateur de la fonction toMap()
     */
    private void selector(Object obj, String key, Map<String, Object> map1) {
        if (obj instanceof Integer) {
            treatment((Integer) obj, key, map1);
        }
        if (obj instanceof int[]) {
            treatment((int[]) obj, key, map1);
        }
    }

    /**
     * Effectue le traitement d'un entier en rajoutant l'entrée dans l'accumulateur si la valeur n'existe pas,
     * fait la somme de toutes les valeurs du tableau sinon et mets ajour l'accumulateur
     * <br><b>Les tableau doivent etre de la meme taille</b>
     *
     * @param obj  Represente l'objet a travailler
     * @param key  Represente la cle de l'objet dans la map
     * @param map1 est l'accumulateur de la fonction toMap()
     */
    private void treatment(int[] obj, String key, Map<String, Object> map1) {
        int[] n1 = (int[]) map1.getOrDefault(key, null);
        if (n1 != null) {
            for (int i = 0; i < n1.length; i++) {
                obj[i] += n1[i];
            }
        }
        map1.put(key, obj);
    }

    /**
     * Effectue le traitement d'un entier en rajoutant l'entrée dans l'accumulateur si la valeur n'existe pas,
     * fait la somme de des valeurs sinon et mets ajour l'accumulateur
     *
     * @param obj  Represente l'objet a travailler
     * @param key  Represente la cle de l'objet dans la map
     * @param map1 est l'accumulateur de la fonction toMap()
     */
    private void treatment(int obj, String key, Map<String, Object> map1) {
        int n1 = (int) map1.getOrDefault(key, 0);
        int val = obj + n1;
        map1.put(key,val);
    }

    /**
     * @return Les resultats en tant que String
     */
    @Override
    public String toString() {
        return subResults.stream().map(AnalyzerPlugin.Result::getResultAsString).reduce("", (acc, cur) -> acc + "\n" + cur);

    }

    /**
     * @return Les resultats dans un format HTML
     */
    public String toHTML() {
        String head = "<head></head>";
        String body = "<body>" + subResults.stream().map(AnalyzerPlugin.Result::getResultAsHtmlDiv).reduce("", (acc, cur) -> acc + cur) + "</body>";
        return "<html>" + head + body + "</html>";
    }

    /*Informations un peu plus détaillées sur ce que renvoient les méthodes toString() et toHTML():
    subResults : résultats obtenus via Analyzer sous forme de liste
    stream() : renvoie subResults en tant que sequential stream (=flux sequentiel)
    map() : applique la fonction entre () à chaque element du stream (ici la fonction écrit chaque element au format String ou HTML)
    reduce() : combine les résultats pour ne renvoyer qu'une seule ligne à la fin
     */
}
