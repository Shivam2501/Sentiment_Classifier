package edu.illinois.cs.cogcomp.sentiment.sentiment;

import com.google.gson.Gson;
import edu.illinois.cs.cogcomp.lbjava.parse.Parser;
import edu.illinois.cs.cogcomp.sentiment.Document;
import edu.illinois.cs.cogcomp.sentiment.twitterTokenizer.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shivambharuka.
 */
public class TwitterReader extends LoadDictionary implements Parser {
    private List<Document> docs;
    private int current;

    /*
    Constructor which reads the original sentences from the Twitter dataset.
    It takes two parameters: File which contains positive sentences and file which
    contains negative sentences.
     */
    public TwitterReader(String pos, String neg) throws IOException {
        setup();
        try{
            read_json(pos,"positive");
            read_json(neg,"negative");
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
    private void setup() throws IOException {
        docs = new ArrayList<>();
        TwitterDictionary td = new TwitterDictionary();
        Features.setupAnnotatorService();
        loadDictionaries();
    }

    private void read_json(String file, String type) throws Exception {
        Gson gson = new Gson();

        FileInputStream fstream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();
        int counter = 0;

        while (line != null){
            if(line.trim().equals("")) {
                line = br.readLine();
                continue;//empty string cannot be parsed
            }

            Tweet entry = gson.fromJson(line,Tweet.class);
            //System.out.println(entry.getText());

            TwitterAnnotation ta = new TwitterAnnotation(entry.getText());
            String annotatedText = "";
            /*for(Hashtag word: ta.getHashtags()) {
               annotatedText += " " + word.getLabels();
            }*/
            for(EnglishWord word: ta.getEnglishWord()) {
                annotatedText += " " + word.getLabel();
            }
            for(Emoticon word: ta.getEmoticons()) {
                annotatedText += " " + word.getLabel();
            }
            for(Abbreviations word: ta.getAbbreviations()) {
                annotatedText += " " + word.getLabel();
            }

            double score = Features.getScore(annotatedText);
            double negationScore = Features.getNegationScore(annotatedText);

            //System.out.println(entry.getId()+ ": " + entry.getText() + ": " + type + ": "+ score + ": " + negationScore);
            docs.add(new Document(entry.getId(), entry.getText(), type, score, negationScore));
            counter++;
            if(counter%10==0)
                System.out.println("Done with "+counter+" Sentences");

            line = br.readLine();
        }

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
