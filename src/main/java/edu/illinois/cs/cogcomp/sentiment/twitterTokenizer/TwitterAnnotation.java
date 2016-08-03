package edu.illinois.cs.cogcomp.sentiment.twitterTokenizer;

import edu.illinois.cs.cogcomp.core.datastructures.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shivambharuka.
 */

public class TwitterAnnotation {
    private List<String> words;
    private List<Pair<String, String>> tokenizedWords;

    public TwitterAnnotation(String text) throws Exception{

        this.words = new ArrayList<>();
        Collections.addAll(this.words, text.split("\\s+"));
        tokenizetext();
    }

    public List<String> getWords() {
        return words;
    }

    public List<String> getLabels() {
        List<String> words = new ArrayList<>();
        for (Pair word: tokenizedWords) {
            words.add((String) word.getSecond());
        }
        return words;
    }

    public String getLabel(int index) {
        return tokenizedWords.get(index).getSecond();
    }

    public String getToken(int index) {
        return tokenizedWords.get(index).getFirst();
    }

    private void tokenizetext() throws Exception {
        tokenizedWords = new ArrayList<>();
        for(String word: words) {
            if(word.trim().equals("")) {
                continue;//empty string cannot be tokenized
            }

            if (word.matches("#[a-zA-Z0-9_]+")) {
                tokenizedWords.add(new Pair<String, String>(word, "H"));
            }
            else if (word.matches("[@＠][a-zA-Z0-9_]+")) {
                tokenizedWords.add(new Pair<String, String>(word, "U"));
            }
            else if(word.matches("(https?:\\/\\/)?(www\\.)?[-a-zA-Z0-9@:" +
                    "%._\\+~#=]+\\.[a-z]+([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")) {
                tokenizedWords.add(new Pair<String, String>(word, "L"));
            }
            else if(Emoticon.containsKey(word)){
                tokenizedWords.add(new Pair<String, String>(word, "E"));
            }
            else if(EnglishWord.containsKey(word.toLowerCase())){
                tokenizedWords.add(new Pair<String, String>(word, "W"));
            }
            else if(Abbreviations.containsKey(word.toLowerCase())){
                tokenizedWords.add(new Pair<String, String>(word, "A"));
            }
            else {
                tokenizedWords.add(new Pair<String, String>(word, "UNK"));
            }
        }
    }

    public List<Abbreviations> getAbbreviations() throws IOException {
        List<Abbreviations> abbreviations = new ArrayList<>();
        for (Pair word: tokenizedWords) {
            if(word.getSecond() == "A") {
                Abbreviations ab = new Abbreviations((String) word.getFirst());
                abbreviations.add(ab);
            }
        }
        return abbreviations;
    }

    public List<EnglishWord> getEnglishWord() throws IOException {
        List<EnglishWord> englishWord = new ArrayList<>();
        for (Pair word: tokenizedWords) {
            if(word.getSecond() == "W") {
                EnglishWord ew = new EnglishWord((String) word.getFirst());
                englishWord.add(ew);
            }
        }
        return englishWord;
    }

    public List<Emoticon> getEmoticons() throws IOException {
        List<Emoticon> emoticons = new ArrayList<>();
        for (Pair word: tokenizedWords) {
            if(word.getSecond() == "E") {
                Emoticon ea = new Emoticon((String) word.getFirst());
                emoticons.add(ea);
            }
        }
        return emoticons;
    }

    public List<Hashtag> getHashtags() {
        List<Hashtag> hastags = new ArrayList<>();
        for (Pair word: tokenizedWords) {
            if(word.getSecond() == "H") {
                Hashtag ha = new Hashtag((String) word.getFirst());
                hastags.add(ha);
            }
        }
        return hastags;
    }

    public Hashtag getHashtag(int index) {
        return new Hashtag((String) tokenizedWords.get(index).getFirst());
    }

    public List<String> getUsernames() {
        List<String> usernames = new ArrayList<>();
        for(Pair word: tokenizedWords) {
            if(word.getSecond() == "U") {
                usernames.add((String) word.getFirst());
            }
        }
        return usernames;
    }


}
