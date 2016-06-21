package edu.illinois.cs.cogcomp.lbjava;

import edu.illinois.cs.cogcomp.annotation.AnnotatorService;
import edu.illinois.cs.cogcomp.core.datastructures.ViewNames;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TokenLabelView;
import edu.illinois.cs.cogcomp.nlp.pipeline.IllinoisPipelineFactory;

import java.util.*;

/**
 * Created by shivambharuka on 6/15/16.
 */

public class Document {
    private final String id;
    private final String sentence;
    private final String label;
    private final List<String> words;

    public Document(String sentence_id, String sentence, String label) {
        this.id = sentence_id;
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

    public List<String> getPOS() {
        String docId = "ListOfPhrases";
        List<String> speech = new ArrayList<String>();
        try {
            AnnotatorService annotator = IllinoisPipelineFactory.buildPipeline();
            TextAnnotation ta = annotator.createAnnotatedTextAnnotation(docId, id, sentence);

            String viewName = ViewNames.POS;

            annotator.addView(ta, viewName);
            TokenLabelView posView = (TokenLabelView) ta.getView(viewName);
            for (int i = 0; i < ta.size(); i++) {
                speech.add(posView.getLabel(i));
            }
            //System.out.println(speech);
            return speech;
        }catch (Exception e) {
            System.err.println("An IOException was caught :"+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
        return speech;
    }
}
