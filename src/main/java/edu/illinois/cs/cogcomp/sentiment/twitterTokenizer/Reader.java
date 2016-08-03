package edu.illinois.cs.cogcomp.sentiment.twitterTokenizer;

/**
 * Created by shivambharuka.
 */

public class Reader {

    public static void main(String[] args) {
        try {
            TwitterDictionary td = new TwitterDictionary();
            TwitterAnnotation ta = new TwitterAnnotation("My #TeenChoice for #ChoiceInternationalArtist is #SuperJunior! :)");
            System.out.println(ta.getWords());
            /*for (Abbreviations word: ta.getAbbreviations()) {
                System.out.println(word.getLabel());
            }
            for (Emoticon word: ta.getEmoticons()) {
                System.out.println(word.getLabel());
            }
            for (EnglishWord word: ta.getEnglishWord()) {
                System.out.println(word.getLabel());
            }*/
            for(Hashtag word: ta.getHashtags()) {
                System.out.println(word.getLabels());
            }
            System.out.println(ta.getLabels());

        }catch (Exception e) {
            System.err.println("IOException was caught :"+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
