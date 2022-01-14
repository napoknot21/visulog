package up.visulog.analyzer;

import org.junit.Assert;
import org.junit.Test;
import up.visulog.analyzer.plugin.CountCommitsPerAuthor;
import up.visulog.config.Configuration;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.*;

public class AnalyzerTest {

    @Test
    public void computeResults() {
    }

    @Test
    public void makePlugin() {
        Configuration c= new Configuration(Paths.get("."),new HashMap<>());
        Analyzer a = new Analyzer(c);
        Optional<AnalyzerPlugin> aa= a.makePlugin("CountCommitsPerAuthor");
        assertFalse(aa.isPresent());
    }

    @Test
    public void makeFakePlugin() {
        Configuration c= new Configuration(Paths.get("."),new HashMap<>());
        Analyzer a = new Analyzer(c);
        Optional<AnalyzerPlugin> aa= a.makePlugin("FakePlugin");
        assertFalse(aa.isPresent());
    }

    @Test
    public void findFakeClassPlugins() {
        try {
            Analyzer.findClassPlugins("FakePlugin");
            } catch(Exception e) {
                Assert.assertEquals(ClassNotFoundException.class, e.getClass());
            }
        }
        @Test
        public void findClassPlugins() {
            try {
                var c = Analyzer.findClassPlugins("CountCommitsPerAuthor");
                Assert.assertEquals(CountCommitsPerAuthor.class,c);
            } catch (ClassNotFoundException e) {
                fail();
            }
        }

        @Test
    public void listOfPlugins() {
        var l = Analyzer.listOfPlugins();
        Assert.assertFalse(l.isEmpty());
    }
}