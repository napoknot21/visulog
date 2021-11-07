package up.visulog.analyzer;

import up.visulog.gitrawdata.*;

import java.io.BufferedReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestCountMergeCommitsPerAuthorPlugin {
    public static void main(String[] args) {
        BufferedReader mergeCommit = ChangesDescription.processCommand("git","log", Paths.get("."));
        List<Commit> commitList = Commit.parseLog(mergeCommit);
        List<Commit> mergeCommitList = new ArrayList<>();
        for(Commit c : commitList){
            if (c instanceof MergeCommit) mergeCommitList.add(c);
        }
        CountCommitsPerAuthorPlugin.Result res = CountMergeCommitsPerAuthorPlugin.processLog(mergeCommitList);
        System.out.println(res.getResultAsHtmlDiv());
    }
}