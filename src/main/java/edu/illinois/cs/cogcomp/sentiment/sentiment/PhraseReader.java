package edu.illinois.cs.cogcomp.sentiment.sentiment;

import edu.illinois.cs.cogcomp.lbjava.parse.Parser;
import edu.illinois.cs.cogcomp.sentiment.Document;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shivambharuka.
 */
public class PhraseReader extends LoadDictionary implements Parser {
    private List<Document> docs;
    private int current;

    /*
Constructor which reads the phrases from the stanford treebank.
It takes the file which contains the phrase_id, sentence_id, phrase, sentiment.
 */
    public PhraseReader(String dir) {
        setup();
        try{
            read(dir);
        }catch (Exception e) {
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
    This function reads the phrase from the stanford treebank.
    It reads each phrase and then creates a new object of Document type
    and stores it in docs.
     */
    private void read(String file) throws Exception {

        FileInputStream fstream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();
        int counter = 0;
        while (line != null) {
            if(line.trim().equals("")) {
                line = br.readLine();
                continue;//empty string cannot be tokenized
            }

            String[] parts = line.split("\t");

            String id = parts[0];
            String phrase = parts[2];
            String sentiment = label(parts[3]);

            if(phrase.trim().equals("")) {
                line = br.readLine();
                continue;
            }

            double score = Features.getScore(phrase);
            double negationScore = Features.getNegationScore(line);

            docs.add(new Document(id, phrase, sentiment, score, negationScore));
            counter++;
            if(counter%100==0)
                System.out.println("Done with "+counter+" Sentences");
            line = br.readLine();
        }
        System.out.println("Done with file");
        br.close();
    }

    /*
    This is a helper function which assigns the labels to the phrase level
    sentiment. It can be used to define the number of buckets in which we want
    to classify our data.
     */
    private String label(String label){
        switch (label) {
            case "0":
                return "Negative";
            case "1":
                return "Negative";
            case "2":
                return "Neutral";
            case "3":
                return "Positive";
            case "4":
                return "Positive";
        }
        return null;
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
