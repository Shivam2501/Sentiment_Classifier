package edu.illinois.cs.cogcomp.lbjava;

import edu.illinois.cs.cogcomp.lbj.pos.POSTag;

import java.util.*;

/**
 * Created by shivambharuka on 6/15/16.
 */

public class Document {
    private final String sentence;
    private final String label;
    private final List<String> words;

    public Document(String sentence, String label) {
        this.sentence = sentence;
        this.label = label;

        List<String> word = new ArrayList<String>();
        String[] arr = sentence.split(" ");
        for( String ss : arr){
            word.add(ss);
        }
        this.words = word;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getWords() {
        return Collections.unmodifiableList(words);
    }

    public String getSentence() {
        return sentence;
    }
}
