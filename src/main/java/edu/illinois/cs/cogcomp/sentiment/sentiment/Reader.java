package edu.illinois.cs.cogcomp.sentiment.sentiment;

import java.util.*;
import java.io.*;

import edu.illinois.cs.cogcomp.annotation.AnnotatorException;
import edu.illinois.cs.cogcomp.annotation.AnnotatorService;
import edu.illinois.cs.cogcomp.annotation.AnnotatorServiceConfigurator;
import edu.illinois.cs.cogcomp.core.datastructures.ViewNames;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TokenLabelView;
import edu.illinois.cs.cogcomp.core.utilities.configuration.Configurator;
import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;
import edu.illinois.cs.cogcomp.sentiment.Document;
import edu.illinois.cs.cogcomp.lbjava.parse.Parser;
import edu.illinois.cs.cogcomp.nlp.common.PipelineConfigurator;
import edu.illinois.cs.cogcomp.nlp.pipeline.IllinoisPipelineFactory;

/**
 * Created by shivambharuka on 6/15/16.
 */
public class Reader implements Parser {
    private List<Document> docs;
    private int current;
    private static AnnotatorService annotator;

    private List<String> positive_words;
    private List<String> negative_words;

    public Reader(String pos, String neg){
        setup();
        docs = new ArrayList<>();
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

    public Reader(String dir) {
        setup();
        // read the train and test files
        docs = new ArrayList<>();
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

    private void setup(){
        // create config for pipeline
        Map<String, String> nonDefaultProps = new HashMap<>();
        nonDefaultProps.put(PipelineConfigurator.USE_NER_CONLL.key, Configurator.FALSE);
        nonDefaultProps.put(PipelineConfigurator.USE_NER_ONTONOTES.key, Configurator.FALSE);
        nonDefaultProps.put(PipelineConfigurator.USE_STANFORD_DEP.key, Configurator.FALSE);
        nonDefaultProps.put(PipelineConfigurator.USE_STANFORD_PARSE.key, Configurator.FALSE);
        nonDefaultProps.put(PipelineConfigurator.USE_LEMMA.key, Configurator.FALSE);
        nonDefaultProps.put(PipelineConfigurator.USE_SHALLOW_PARSE.key, Configurator.FALSE);
        nonDefaultProps.put(PipelineConfigurator.USE_SRL_NOM.key, Configurator.FALSE);
        nonDefaultProps.put(PipelineConfigurator.USE_SRL_VERB.key, Configurator.FALSE);
        nonDefaultProps.put(AnnotatorServiceConfigurator.DISABLE_CACHE.key, Configurator.TRUE);

        ResourceManager rm = new PipelineConfigurator().getConfig(nonDefaultProps);

        try {
            annotator = IllinoisPipelineFactory.buildPipeline(rm);
        } catch (IOException | AnnotatorException e) {
            e.printStackTrace();
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

    private void read_file(String file) throws Exception {

        FileInputStream fstream = new FileInputStream("data/"+file+"-words.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();

        while (line != null) {
            if(line.trim().equals(""))   //check for empty string
                continue;
            if(file.equals("negative"))
                negative_words.add(line);
            if(file.equals("positive"))
                positive_words.add(line);
            line = br.readLine();
        }

        br.close();
    }

    private void read_line(String file, String type) throws Exception {

        FileInputStream fstream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();
        int counter = 0;
        String id = "0";
        while (line != null) {
            if(line.trim().equals("")) {
                line = br.readLine();
                continue;//empty string cannot be tokenized
            }

            double score = getScore(line);

            docs.add(new Document(id, line, type, score));
            counter++;
            if(counter%100==0)
                System.out.println("Done with "+counter+" Sentences");
            line = br.readLine();
        }
        System.out.println("Done with file");
        br.close();
    }

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

            double score = getScore(phrase);

            docs.add(new Document(id, phrase, sentiment, score));
            counter++;
            if(counter%100==0)
                System.out.println("Done with "+counter+" Sentences");
            line = br.readLine();
        }
        System.out.println("Done with file");
        br.close();
    }

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

    private double getScore(String words) {
        double score = 0;
        for(String word:words.split("\\s+")){
            if(positive_words.contains(word))
                score++;
            if(negative_words.contains(word))
                score--;
        }
        return score;
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

    public static List<String> getPOS(String id, String sentence) {
        String docId = "ListOfPhrases";
        String temp;
        List<String> speech = new ArrayList<>();
        try {

            TextAnnotation ta = annotator.createAnnotatedTextAnnotation(docId, id, sentence);

            String viewName = ViewNames.POS;

            annotator.addView(ta, viewName);
            TokenLabelView posView = (TokenLabelView) ta.getView(viewName);
            for (int i = 0; i < ta.size(); i++) {
                temp = posView.getLabel(i);
                if(temp.matches("^[JR]"))
                    speech.add(temp);
            }

            return speech;
        }catch (Exception e) {
            System.err.println("IOException was caught :"+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
        return speech;
    }
}
