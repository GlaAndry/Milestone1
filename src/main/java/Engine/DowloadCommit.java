package engine;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ConfigurationDecoder;
import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.commons.configuration2.interpol.ConfigurationInterpolator;
import org.apache.commons.configuration2.interpol.Lookup;
import org.apache.commons.configuration2.sync.LockMode;
import org.apache.commons.configuration2.sync.Synchronizer;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


import java.util.logging.Logger;

public class DowloadCommit {



    private static final Logger LOGGER = Logger.getLogger(DowloadCommit.class.getName());

    //String path = "C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\GitDir";
    //String commitPath = "C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\commits.txt";
    //String completePath = "C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\GitDir\\.git";

    String path = "";
    String commitPath = "";
    String completePath = "";

    public void getAllCommits() throws GitAPIException {

        ////////////////carico i dati da config.properties
        try (InputStream input = new FileInputStream("C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\config.properties")) {

            Properties prop = new Properties();
            // load a properties file
            prop.load(input);

            path = prop.getProperty("gitDirPath");
            commitPath = prop.getProperty("commitPath");
            completePath = prop.getProperty("gitPath");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ///////////////////////////////////////

        File dir = new File(path);

        if(!dir.exists()) {
            LOGGER.info("Comando: Clone Repository");
            dir.mkdir();
            Git.cloneRepository()
                    .setURI("https://github.com/apache/qpid.git")
                    .setDirectory(dir)
                    .call();
            LOGGER.info("Clone Repository eseguito correttamente.\n\n");
            LOGGER.info("Eseguire nuovamente per scaricare tutti i commit.\n");
        }

        try(FileWriter fileWriter = new FileWriter(commitPath)){
                //Impostazione di Git e della repo.
                Git git = Git.open(new File(completePath));

                Repository repository = FileRepositoryBuilder.create(new File(completePath));
                LOGGER.info(String.valueOf(repository));
                List<Ref> branches = git.branchList().call();
                for (Ref ref : branches)
                {

                    LOGGER.info(ref.getName());

                }

                Iterable<RevCommit> commits = git.log().all().call();

                for (RevCommit revCommit : commits) { //itero tutti i commit.

                    fileWriter.append("COMMIT:");
                    fileWriter.append(revCommit.getFullMessage()); //Scrittura del commit message.
                    fileWriter.append("\n");
                    fileWriter.append("TIME:");

                    //cast della data per scriverla all'interno del file...
                    String pattern = "MM/dd/yyyy HH:mm:ss";
                    DateFormat df = new SimpleDateFormat(pattern);
                    String date = df.format(revCommit.getAuthorIdent().getWhen());
                    fileWriter.append(date);
                    fileWriter.append("\n"); //Scrittura della data eseguita
                }

            } catch (IOException e){
                e.printStackTrace();
            }

        }

    public static void main(String[] args) throws GitAPIException {

        LOGGER.info( "Scrivo tutti i commit eseguiti fino a questo momento all'interno del file.\n");
        new DowloadCommit().getAllCommits();
        LOGGER.info("Fatto!!\n");
    }
}
