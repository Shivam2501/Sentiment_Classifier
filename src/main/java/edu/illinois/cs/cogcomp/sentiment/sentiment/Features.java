package edu.illinois.cs.cogcomp.sentiment.sentiment;

import edu.illinois.cs.cogcomp.annotation.AnnotatorException;
import edu.illinois.cs.cogcomp.annotation.AnnotatorService;
import edu.illinois.cs.cogcomp.annotation.AnnotatorServiceConfigurator;
import edu.illinois.cs.cogcomp.core.datastructures.ViewNames;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TokenLabelView;
import edu.illinois.cs.cogcomp.core.utilities.configuration.Configurator;
import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;
import edu.illinois.cs.cogcomp.nlp.common.PipelineConfigurator;
import edu.illinois.cs.cogcomp.nlp.pipeline.IllinoisPipelineFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shivambharuka.
 */
public class Features extends LoadDictionary{

    private static AnnotatorService annotator;

    static double getNegationScore(String words) {
        double score = 0;
        Integer count = 1;
        for(String word:words.split("\\s+")) {
            if(negation_words.contains(word.toLowerCase()))
                count++;
            if(positive_words.contains(word.toLowerCase())) {
                if(count%2 == 1)
                    score++;
                else
                    score--;
            }
            if(negative_words.contains(word.toLowerCase())) {
                if(count%2 == 1)
                    score--;
                else
                    score++;
            }
        }
        return score;
    }

    /*
    This is a helper function which iterates through each word
    in a sentence or a phrase and assigns it a gazetteer score.
    It looks up if the word if present in the gazetteer and then
    increment/decrement the score accordingly.
     */
    static double getScore(String words) {
        double score = 0;
        for(String word:words.split("\\s+")){
            if(positive_words.contains(word.toLowerCase()))
                score++;
            if(negative_words.contains(word.toLowerCase()))
                score--;
            //if(dictionary.containsKey(word.toLowerCase())){
            //    score += Double.parseDouble(dictionary.get(word));
            //}
        }

        return score;
    }

    static void setupAnnotatorService() {
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
    }

    /*
    This helper function annotates the sentence/phrase and then attaches
    the parts of speech view to it. It returns all the adjectives and adverbs
    from a sentence/phrase.
     */
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
                if(temp.matches("JJ\\S*") || temp.matches("RB\\S*"))
                    speech.add(ta.getToken(i));
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
