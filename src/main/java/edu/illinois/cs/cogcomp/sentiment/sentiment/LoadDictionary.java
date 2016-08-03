package edu.illinois.cs.cogcomp.sentiment.sentiment;

import edu.illinois.cs.cogcomp.annotation.AnnotatorException;
import edu.illinois.cs.cogcomp.nlp.pipeline.IllinoisPipelineFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shivambharuka.
 */
class LoadDictionary {

    static List<String> positive_words;
    static List<String> negative_words;
    Map<String,String> dictionary; //yelp dataset
    static List<String> negation_words;

    /*
    This function reads the Yelp dataset dictionary and stores the (word,score) in a map.
     */
    private void read_assessment_file() throws Exception {
        FileInputStream fstream = new FileInputStream("data/map.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        dictionary = new HashMap<>();
        String line = br.readLine();
        while(line!=null){
            if(line.trim().equals("")) {
                line = br.readLine();
                continue;//empty string cannot be tokenized
            }

            String[] parts = line.split(",");

            String word = parts[0];
            String score = parts[6];

            dictionary.put(word,score);
            line = br.readLine();
        }
        br.close();
    }

    /*
   This function reads the negation list.
   */
    private void read_negation() throws Exception {

        negation_words = new ArrayList<>();
        FileInputStream fstream = new FileInputStream("data/negation.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();

        while (line != null) {
            if(line.trim().equals("")) {   //check for empty string
                line = br.readLine();
                continue;
            }

            negation_words.add(line.toLowerCase());
            line = br.readLine();
        }

        br.close();
    }

    /*
    This function reads the gazetteer and stores the positive and negative words in the
    respective list.
     */
    private void read_file(String file) throws Exception {

        FileInputStream fstream = new FileInputStream("data/"+file+"-words.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();

        while (line != null) {
            if(line.trim().equals("")) {   //check for empty string
                line = br.readLine();
                continue;
            }
            if(file.equals("negative"))
                negative_words.add(line);
            if(file.equals("positive"))
                positive_words.add(line);
            line = br.readLine();
        }

        br.close();
    }

    void loadDictionaries() {
        try{
            read_assessment_file();
        }catch (Exception e) {
            System.err.println("An IOException was caught :"+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }

        try{
            read_negation();
        }catch (Exception e) {
            System.err.println("An IOException was caught :"+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }

        // read the positive and negative words file
        positive_words = new ArrayList<>();
        try{
            read_file("positive");
        }catch (Exception e){
            System.err.println("An IOException was caught :"+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }

        negative_words = new ArrayList<>();
        try{
            read_file("negative");
        }catch (Exception e){
            System.err.println("An IOException was caught :"+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
