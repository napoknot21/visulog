package up.visulog.ui.model;

import up.visulog.analyzer.AnalyzerResult;

import java.util.Map;

public class Model implements MapRelations {

    private AnalyzerResult result;


    public void update(AnalyzerResult result) {
        this.result = result;

    }

    public String toHtml() { // retourne le resultats sous html
        if (result == null) return "";
        System.out.println(result.toHTML());
        return result.toHTML();
    }

    public Map<String,Integer> getResultAsMap () {
        return this.result.toMap();
    }

    public AnalyzerResult getResult() {
        return result;
    }

}
