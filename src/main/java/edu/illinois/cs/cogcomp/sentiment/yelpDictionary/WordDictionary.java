package edu.illinois.cs.cogcomp.sentiment.yelpDictionary;

/**
 * Created by shivambharuka on 7/5/16.
 */
public class WordDictionary {
    private String word;
    public Integer fiveStar = 0;
    public Integer fourStar = 0;
    public Integer threeStar = 0;
    public Integer twoStar = 0;
    public Integer oneStar = 0;

    public WordDictionary(String word){
        this.word = word;
    }
}
