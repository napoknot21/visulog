package up.visulog.analyzer.plugin;

import org.junit.Test;
import up.visulog.gitrawdata.ChangesDescription;
import up.visulog.gitrawdata.Commit;
import up.visulog.gitrawdata.MergeCommit;

import java.io.BufferedReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class CountMergeCommitsPerAuthorPluginTest {

    @Test
    public void processLog() {
        BufferedReader mergeCommit = ChangesDescription.processCommand(new String[]{"git", "log"}, Paths.get("."));
        List<Commit> commitList = Commit.parseLog(mergeCommit);
        List<Commit> mergeCommitList = new ArrayList<>();
        for (Commit c : commitList) {
            if (c instanceof MergeCommit) mergeCommitList.add(c);
        }
        CountMergeCommitsPerAuthor.Result res = CountMergeCommitsPerAuthor.processLog(mergeCommitList);
        assertNotNull(res);
        System.out.println(res.getResultAsHtmlDiv());
    }

}