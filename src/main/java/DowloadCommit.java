
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class DowloadCommit {

    File dir = new File("C:/Users/Alessio Mazzola/Desktop/Prove ISW2/Milestone1Maven/src/main/resources/GitDir/");
    String path = "C:/Users/Alessio Mazzola/Desktop/Prove ISW2/Milestone1Maven/src/main/resources/GitDir/";
    String suffix = ".git";
    String completePah = path + suffix;


    public void getAllCommits() throws GitAPIException, IOException {

        if(!dir.exists()){
            System.out.println("Comando: Clone Repository");
            dir.mkdir();
            Git git = Git.cloneRepository()
                    .setURI("https://github.com/apache/qpid.git")
                    .setDirectory(dir)
                    .call();
            System.out.println("Clone Repository eseguito correttamente.\n\n");
            System.out.println("Eseguire nuovamente per scaricare tutti i commit.\n");
        } else {
            int counter = 0;

            //Puntatore al file su cui eseguire la scrittura.
            FileWriter fileWriter = new FileWriter("C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\commits.txt");

            //Impostazione di Git e della repo.
            Git git = Git.open(new File(completePah));

            Repository repository = FileRepositoryBuilder.create(new File(completePah));
            System.out.println(repository);
            List<Ref> branches = git.branchList().call();
            for (Ref ref : branches)
            {

                System.out.println(ref.getName());

            }

            Iterable<RevCommit> commits = git.log().all().call();

            for (RevCommit revCommit : commits) { //itero tutti i commit.
                //System.out.println(counter);
                //counter++;
                //RevTree tree=revCommit.getTree();  //Altri usi
                fileWriter.append("COMMIT:");
                fileWriter.append(revCommit.getFullMessage()); //Scrittura del commit message.
                fileWriter.append("\n");
                fileWriter.append("TIME:");

                //cast della data per scriverla all'interno del file...
                String pattern = "MM/dd/yyyy HH:mm:ss";
                DateFormat df = new SimpleDateFormat(pattern);
                String date = df.format(revCommit.getAuthorIdent().getWhen());
                fileWriter.append(date);
                //Scrittura della data eseguita
                fileWriter.append("\n");


                //////////////////////////////////////////////////////////////////////////////////////////////////////
                //ELEMENTI UTILI NON UTILIZZATI.....................
                /*System.out.println(revCommit.getName());*/
                //System.out.println("COMMIT : "+revCommit);
                //System.out.println("COMMIT MESSAGE : "+revCommit.getFullMessage());

                //System.out.println("AUTHOR NAME : "+revCommit.getAuthorIdent().getName());
                //System.out.println("AUTHOR E-MAIL ID : "+revCommit.getAuthorIdent().getEmailAddress());
                //System.out.println("DATE/TIME : "+revCommit.getAuthorIdent().getWhen());
                /*
                Status status=git.status().call();
                System.out.println("added:"+status.getAdded());
                System.out.println("removed:"+status.getRemoved());

                TreeWalk treewalk = new TreeWalk(repository);
                treewalk.addTree(tree);
                treewalk.setRecursive(true);
                while (treewalk.next())
                {
                    if(treewalk.getNameString().contains("QPID"))
                        System.out.println("FILE COMMITTED : "+treewalk.getNameString());
                }
                */
                ///////////////////////////////////////////////////////////////////////////////////////////////////////
            }
            //Chiusura file writer
            fileWriter.close();
        }


        }



    public static void main(String[] args) throws GitAPIException, IOException {

        System.out.println("Scrivo tutti i commit eseguiti fino a questo momento all'interno del file.\n");
        new DowloadCommit().getAllCommits();
        System.out.println("Fatto!!\n");
    }
}
