package edu.illinois.cs.cogcomp.lbjava.sentiment;

import java.util.*;
import java.io.*;

import edu.illinois.cs.cogcomp.lbjava.Document;
import edu.illinois.cs.cogcomp.lbjava.parse.Parser;

/**
 * Created by shivambharuka on 6/15/16.
 */
public class Reader implements Parser {
    private List<Document> docs;
    private int current;

    public Reader(String dir) {
        docs = new ArrayList<Document>();
        try{
            read(dir);
        }catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Collections.shuffle(docs);
        current = 0;
    }

    private void read(String file) throws Exception {

        FileInputStream fstream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();

        while (line != null) {

            String[] parts = line.split("\t");
            String phrase = parts[2];
            String sentiment = label(parts[3]);

            docs.add(new Document(phrase,sentiment));
            line = br.readLine();
        }

        br.close();
    }

    public String label(String label){
        if(label.equals("0") || label.equals("1")){
            return "Negative";
        }else if(label.equals("1")){
            return "Somewhat Negative";
        }else if(label.equals("2")){
            return "Neutral";
        }else if(label.equals("3")){
            return "Somewhat Positive";
        }else if(label.equals("4")){
            return "Positive";
        }
        return null;
    }

    public void close() {}

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
