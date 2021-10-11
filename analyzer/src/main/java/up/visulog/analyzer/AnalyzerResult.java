package up.visulog.analyzer;

import java.util.List;

public class AnalyzerResult { //classe qui contient les resultats obtenus via Analyzer sous forme de liste
    public List<AnalyzerPlugin.Result> getSubResults() {
        return subResults;
    } //renvoie la liste des resultats

    private final List<AnalyzerPlugin.Result> subResults; //liste des resultats

    public AnalyzerResult(List<AnalyzerPlugin.Result> subResults) { //constructeur de la classe
        this.subResults = subResults;
    }

    @Override
    public String toString() { //renvoie les resultats en tant que chaîne de caractère
        return subResults.stream().map(AnalyzerPlugin.Result::getResultAsString).reduce("", (acc, cur) -> acc + "\n" + cur);
    }

    public String toHTML() { //renvoie les resultats dans un format HTML
        return "<html><body>"+subResults.stream().map(AnalyzerPlugin.Result::getResultAsHtmlDiv).reduce("", (acc, cur) -> acc + cur) + "</body></html>";
    }
}
