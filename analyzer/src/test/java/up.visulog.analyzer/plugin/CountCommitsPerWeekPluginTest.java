package up.visulog.analyzer.plugin;

import org.junit.Assert;
import org.junit.Test;
import up.visulog.gitrawdata.Commit;

import java.nio.file.Path;

public class CountCommitsPerWeekPluginTest {

    @Test
    public void processLog() {
        var list = Commit.parseLogFromCommand(Path.of("."));
        Assert.assertFalse(list.isEmpty());
        var result = CountCommitsPerWeek.processLog(list);
        Assert.assertNotNull(result);
        Assert.assertFalse(result.getResultAsMap().isEmpty());
        Assert.assertFalse(result.getResultAsString().isBlank());
        Assert.assertFalse(result.getResultAsHtmlDiv().isBlank());
    }
}