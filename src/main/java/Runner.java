import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public class Runner {

    public static void main(String[] args) throws GitAPIException, IOException {

        String results = "C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\results.csv";
        String FinRes = "C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\finRes.csv";

        DowloadCommit dowloadCommit = new DowloadCommit();
        //RetrieveTicketsID retrieveTicketsID = new RetrieveTicketsID();
        Searcher searcher = new Searcher();


        dowloadCommit.getAllCommits();
        searcher.compareCSV(FinRes,results);

    }
}
