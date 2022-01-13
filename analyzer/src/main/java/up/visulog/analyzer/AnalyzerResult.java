package up.visulog.analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AnalyzerResult { //classe qui contient les resultats obtenus via Analyzer sous forme de liste
    private final List<AnalyzerPlugin.Result> subResults; //liste des resultats

    public AnalyzerResult(List<AnalyzerPlugin.Result> subResults) { //constructeur de la classe
        this.subResults = subResults;
    }

    public List<AnalyzerPlugin.Result> getSubResults() {
        return subResults;
    } //renvoie la liste des resultats

    @SuppressWarnings("all")
    public <C> Map<String, C> toMap() { //Renvoi les resultats en tant que map
        return (Map<String, C>) subResults.stream().map(AnalyzerPlugin.Result::getResultAsMap).reduce(new HashMap<>(), (map1, map2) -> {
            if (map2 != null) {
                map2.forEach((key, obj) -> selector(obj, key, map1));
            }
            return map1;
        });
    }

    /**
     * Choisis le traitement approprie en fonction du type de C
     *
     * @param obj  Represente l'objet a travailler
     * @param key  Represente la cle de l'objet dans la map
     * @param map1 est l'accumulateur de la fonction toMap()
     * @param <C>  represente le type quelconque de la classe des valeurs a traiter
     */
    private <C> void selector(C obj, String key, Map<String, C> map1) {
        if (obj instanceof Integer) {
            numberTreatment(obj, key, map1);
        }
        if (obj instanceof int[]) {
            numberArrayTreatment(obj, key, map1);
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
     * @param <C>  represente le type quelconque de la classe des valeurs a traiter
     */
    private <C> void numberArrayTreatment(C obj, String key, Map<String, C> map1) {
        if (!(obj instanceof int[])) return;
        int[] n1 = (int[]) map1.getOrDefault(key, null);
        if (n1 != null) {
            for (int i = 0; i < n1.length; i++) {
                ((int[]) obj)[i] += n1[i];
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
     * @param <C>  represente le type quelconque de la classe des valeurs a traiter
     */
    @SuppressWarnings("all")
    private <C> void numberTreatment(C obj, String key, Map<String, C> map1) {
        if (!(obj instanceof Integer)) return;
        C n1 = map1.getOrDefault(key, null);
        if (n1 == null) {
            map1.put(key, obj);
            return;
        }
        int val = (int) obj + (int) n1;
        ((Map<String, Integer>) map1).put(key, val);
    }

    @Override
    public String toString() { //renvoie les resultats en tant que String
        return subResults.stream().map(AnalyzerPlugin.Result::getResultAsString).reduce("", (acc, cur) -> acc + "\n" + cur);

    }

    public String toHTML() { //renvoie les resultats dans un format HTML
        String head = "<head></head>";
        String body = "<body>" + subResults.stream().map(AnalyzerPlugin.Result::getResultAsHtmlDiv).reduce("", (acc, cur) -> acc + cur) + "</body>";
        return "<html>" + head + body + "</html>";
    }

    /*Informations un peu plus détaillées sur ce que renvoient les méthodes toString() et toHTML():
    subResults : résultats obtenus via Analyzer sous forme de liste
    stream() : renvoie subResults en tant que sequential stream (=flux sequentiel)
    map() : applique la fontion entre () à chaque element du stream (ici la fonction ecrit chaque element au format String ou HTML)
    reduce() : combine les resultats pour ne renvoyer qu'une seule ligne à la fin
     */
}
