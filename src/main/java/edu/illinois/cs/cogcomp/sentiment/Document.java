package edu.illinois.cs.cogcomp.sentiment;

import edu.illinois.cs.cogcomp.sentiment.sentiment.Features;
import edu.illinois.cs.cogcomp.sentiment.sentiment.Reader;

import java.util.*;

/**
 * Created by shivambharuka on 6/15/16.
 */

public class Document {
    private final String id;
    private final String sentence;
    private final String label;
    private final List<String> words;
    private final double score;
    private final double negationScore;

    public Document(String sentence_id, String sentence, String label, double score, double negationScore) {
        this.id = sentence_id;
        this.sentence = sentence;
        this.label = label;
        this.score = score;
        this.negationScore = negationScore;

        List<String> word = new ArrayList<>();
        String[] arr = sentence.split(" ");
        Collections.addAll(word, arr);
        this.words = word;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getWords() {
        return Collections.unmodifiableList(words);
    }

    public List<String> getPOS() {
        return Features.getPOS(id,sentence);
    }

    public String getScore() {
       if(score > 0)
           return "Positive";
       else if(score < 0)
           return "Negative";
       else return "Neutral";
    }

    public String getNegationScore() {
        if(negationScore > 0)
            return "Positive";
        else if(negationScore < 0)
            return "Negative";
        else return "Neutral";
    }
}
