package up.visulog.analyzer;

import org.junit.Test;
import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;
import up.visulog.gitrawdata.CommitBuilder;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.*;

public class TestCountCommitsPerAuthorPlugin {
    /* Let's check whether the number of authors is preserved and that the sum of the commits of each author is equal to the total number of commits */
    @Test
   public static void checkCommitSum() {
        var log = new ArrayList<Commit>();
        String[] authors = {"foo", "bar", "baz"};
        var entries = 20;
        for (int i = 0; i < entries; i++) {
            log.add( new CommitBuilder("").setAuthor(authors[i % 3]).createCommit());
        }
        var res = CountCommitsPerAuthorPlugin.processLog(log);
        assertEquals(authors.length, res.getResultAsMap().size());
        var sum = res.getResultAsMap().values()
                .stream().reduce(0, Integer::sum);
        assertEquals(entries, sum.longValue());
        System.out.print(res.getResultAsHtmlDiv());
    }

    public static void main(String[] args) {
        Configuration c= new Configuration(Paths.get("."),new HashMap<>());
        Analyzer a = new Analyzer(c);
        Optional<AnalyzerPlugin> aa= a.makePlugin("CountCommitsPerAuthor");
        assertFalse(aa.isPresent());
    }
}
