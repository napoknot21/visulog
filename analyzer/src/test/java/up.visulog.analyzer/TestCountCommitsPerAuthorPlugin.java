package up.visulog.analyzer;

import org.junit.Assert;
import org.junit.Test;

import up.visulog.analyzer.plugin.CountCommitsPerAuthorPlugin;

import up.visulog.gitrawdata.Commit;
import up.visulog.gitrawdata.CommitBuilder;

import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestCountCommitsPerAuthorPlugin {
    /* Let's check whether the number of authors is preserved and that the sum of the commits of each author is equal to the total number of commits */

    @Test
    public void processLog() {
        var list = Commit.parseLogFromCommand(Path.of("."));
        Assert.assertFalse(list.isEmpty());
        var result = CountCommitsPerAuthorPlugin.processLog(list);
        Assert.assertNotNull(result);
        Assert.assertFalse(result.getResultAsMap().isEmpty());
        Assert.assertFalse(result.getResultAsString().isBlank());
        Assert.assertFalse(result.getResultAsHtmlDiv().isBlank());
    }

    @Test
    public void checkCommitSum() {
        var log = new ArrayList<Commit>();
        String[] authors = {"foo", "bar", "baz"};
        var entries = 20;
        for (int i = 0; i < entries; i++) {
            log.add(new CommitBuilder("11111111111111124241535").setAuthor(authors[i % 3]).createCommit());
        }
        var res = CountCommitsPerAuthorPlugin.processLog(log);
        assertEquals(authors.length, res.getResultAsMap().size());
        var sum = res.getResultAsMap().values()
                .stream().reduce(0, Integer::sum);
        assertEquals(entries, sum.longValue());
        System.out.print(res.getResultAsHtmlDiv());
    }
}
