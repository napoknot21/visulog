package up.visulog.analyzer;

import up.visulog.config.Configuration;

public abstract class Count implements AnalyzerPlugin {
    private final Configuration configuration;
    private CountCommitsPerAuthorPlugin.Result result;

    public Count(Configuration generalConfiguration){
        this.configuration = generalConfiguration;
    }

    public abstract Result processLog();



    }
}
