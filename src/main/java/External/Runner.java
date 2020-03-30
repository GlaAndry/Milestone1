package External;

import Engine.DowloadCommit;
import Engine.Searcher;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public class Runner {

    public static void main(String[] args) throws GitAPIException, IOException {

        /*
        Nota bene: Eseguire solamente dopo che è stato eseguito RetriveTicketID.
         */


        DowloadCommit dowloadCommit = new DowloadCommit();
        Searcher searcher = new Searcher();

        dowloadCommit.getAllCommits();
        searcher.compareCSV();

    }
}
