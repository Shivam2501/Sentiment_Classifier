package edu.illinois.cs.cogcomp.sentiment.sentiment;

import java.util.*;
import java.io.*;

import edu.illinois.cs.cogcomp.sentiment.Document;
import edu.illinois.cs.cogcomp.lbjava.parse.Parser;

/**
 * Created by shivambharuka.
 */

public class Reader extends LoadDictionary implements Parser {
    private List<Document> docs;
    private int current;

    /*
    Constructor which reads the original sentences from the Pang and Lee dataset.
    It takes two parameters: File which contains positive sentences and file which
    contains negative sentences.
     */
    public Reader(String pos, String neg){
        setup();
        try{
            read_line(pos,"positive");
            read_line(neg, "negative");
        }catch (Exception e){
            System.err.println("An IOException was caught :"+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
        Collections.shuffle(docs);
        current = 0;
    }

    /*
    This is the setup function which initializes the Illinois pipeline and loads the
    gazetteer which has positive and negative words.
     */
    private void setup(){
        docs = new ArrayList<>();
        Features.setupAnnotatorService();
        loadDictionaries();
    }

    /*
    This function reads the sentences from the Pang and Lee dataset.
    It reads each sentence and then creates a new object of Document type
    and stores it in docs.
     */
    private void read_line(String file, String type) throws Exception {

        FileInputStream fstream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();
        int counter = 0;
        int id = 0;
        while (line != null) {
            if(line.trim().equals("")) {
                line = br.readLine();
                continue;//empty string cannot be tokenized
            }

            double score = Features.getScore(line);
            double negationScore = Features.getNegationScore(line);

            /*if(negationScore>0 && Objects.equals(type, "negative") ||
                    negationScore<0 && Objects.equals(type, "positive")) {
                //negationScore *= -1;
                System.out.println(line + ": " + negationScore + ": " + type);
            }*/

            docs.add(new Document(Integer.toString(id), line, type, score, negationScore));
            counter++;
            if(counter%100==0)
                System.out.println("Done with "+counter+" Sentences");
            id ++;
            line = br.readLine();
        }
        System.out.println("Done with file");
        br.close();
    }

    public void close() {}

    /*
    This function iterates through each entry in docs list
    and returns it to the classifier.
     */
    public Object next() {
        if (current < docs.size())
            return docs.get(current++);
        else
            return null;
    }

    public void reset() {
        current = 0;
    }

}
