import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;
import org.eclipse.jgit.util.FileUtils;

import java.io.*;
import java.util.*;

public class Searcher {

    String path = "C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\commits.txt";
    String pathNewFile = "C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\NEWcommits.txt";
    String pathFinalFile = "C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\FinalCommits.txt";
    String testPath = "C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\test.txt";
    String proj = "QPID";
    int linea  = 0;

    private void lastIssue() throws IOException,StringIndexOutOfBoundsException{

        File file = new File(path);
        FileWriter fw = new FileWriter(pathNewFile);


        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            linea++;
            if (line.contains("TIME")){
                try{
                    String tempo = line.substring(5,15);
                    System.out.println(linea);
                    //System.out.println("Printo il tempo:");
                    //System.out.println(tempo);
                    fw.append(tempo);
                    fw.append("\n");

                }catch (StringIndexOutOfBoundsException e){
                    e.printStackTrace();
                }


            } else if (line.contains("COMMIT:")) {
                if(line.contains("QPID")){
                    try{
                        System.out.println(linea);

                        String commmit = line.substring(7,16);
                        //System.out.println("Printo il ticket:");
                        //System.out.println(commmit);
                        fw.append(commmit);
                        fw.append("\n");
                    } catch (StringIndexOutOfBoundsException e){
                        e.printStackTrace();
                    }

                }
                //System.out.println(linea);

                System.out.println("Nessun Parametro corrispondente nella linea: " + linea);

            } else {
                //System.out.println(linea);

                System.out.println("Nessun Parametro corrispondente nella linea: " + linea);
            }


        }
        fw.close();
        scanner.close();

    }

    private void removeElements() throws IOException {

        File file = new File(pathNewFile);
        FileWriter fw = new FileWriter(pathFinalFile);

        int counter = 0;
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){

            //Compongo un file che al suo interno ha solo coppie DATA - Ticket
            String line = scanner.nextLine();
            String line2 = scanner.nextLine();
            /*Necessario in quanto se eseguo scanner.nextline() automaticamente salta alla linea successiva anche line,
            * Quindi vado avanti a controllare a due a due.
            * */


            if((Character.isDigit(line.charAt(0)) && line2.contains("QPID"))){
                fw.append(line);
                fw.append("\n");
            }
            if(line2.contains("QPID")){
                fw.append(line2);
                fw.append("\n");
            }
            counter ++;



        }
        scanner.close();
        fw.close();
    }

    private void createTicketCSV() throws FileNotFoundException {

        String path = "C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\finRes.csv";
        File file2 = new File(path);

        int counter = 0;

        File file = new File(pathFinalFile);
        Scanner scanner = new Scanner(file);
        //HashMap<String,String> hashMap = new HashMap<String,String>();
        List<String[]> data = new ArrayList<String[]>();

        while (scanner.hasNextLine()){
            String line1 = scanner.nextLine();
            String line2 = scanner.nextLine();
            // create a List which contains String array

            //Aggiungo lo spacer iniziale per CSv
            data.add(new String[] {line1,line2});
        }
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file2);
            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeAll(data);
            writer.close(); //Chiusura del file writer.
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    private void removeDuplicatesFromCSV(String path1, String path2) throws IOException {

        try {
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader1 = new FileReader(path1);
            FileReader fileReader2 = new FileReader(path2);
            // create csvReader object and skip first Line
            CSVReader csvReader1 = new CSVReader(filereader1);
            CSVReader csvReader2 = new CSVReader(fileReader2);


            ArrayList<String[]> lista = new ArrayList<String[]>();
            ArrayList<String[]> finlis = new ArrayList<String[]>();


            List<String[]> uno = csvReader1.readAll();
            List<String[]> due = csvReader2.readAll();

            int cont = 0;
            for (String[] record1 : uno) {
                for(String[] record2: due){
                    if (record1[1].equals(record2[0])) {
                        //cont++;
                        if (cont == 0){
                            lista.add(new String[] {record1[0], record1[1]});
                        }
                        cont ++;
                    }
                }
                cont = 0;

            }

            String appoggio = ""; // Stringa di appoggio per determinare un valore in comune.

            for(String[] lis : lista){
                for (String[] lis2 : lista){
                    if (lis[1].equals(lis2[1])) {
                        //cont++;
                        if (cont == 0){
                            appoggio = lis2[1];
                            finlis.add(new String[] {lis[0], lis[1]});
                        }
                        cont ++;
                    }
                }
                if(!(lis[1].equals(appoggio))){
                    cont = 0;
                }

            }

            FileWriter fileWriter = new FileWriter("C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\fin.csv");
            CSVWriter writer = new CSVWriter(fileWriter);

            writer.writeAll(finlis);
            writer.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void compareCSV(String path1, String path2) throws IOException {

        new Searcher().lastIssue();
        new Searcher().removeElements();
        new Searcher().createTicketCSV();
        new Searcher().removeDuplicatesFromCSV(path1, path2);

    }

    public static void main(String[] args) throws IOException {

        String results = "C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\results.csv";
        String FinRes = "C:\\Users\\Alessio Mazzola\\Desktop\\Prove ISW2\\Milestone1Maven\\src\\main\\resources\\finRes.csv";

        new Searcher().compareCSV(results,FinRes);
    }
}
