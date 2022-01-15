package up.visulog.analyzer.plugin;

import org.junit.Assert;
import org.junit.Test;
import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class CountLinesPerAuthorPluginTest {

    @Test
    public void process() {
        var list = Commit.parseLogFromCommand(Path.of("."));
        Assert.assertFalse(list.isEmpty());
        var result = CountLinesPerAuthor.process(new Configuration(Paths.get("."), new HashMap<>()));
        Assert.assertNotNull(result);
        Assert.assertFalse(result.getResultAsMap().isEmpty());
        Assert.assertFalse(result.getResultAsString().isBlank());
        Assert.assertFalse(result.getResultAsHtmlDiv().isBlank());
    }
}