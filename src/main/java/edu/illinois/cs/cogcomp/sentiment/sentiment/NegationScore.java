// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000B49CC2E4E2A4D294550FB4D4F4C29CCCFCB0E4ECF2A45D079CF4E2DCD4DCB215841D450B1D558A6580E292ACCCB47582609CA28DA24A8E5A7A690AA61D4B6500A145A549701546DA05B00015D9F217C5000000

package edu.illinois.cs.cogcomp.sentiment.sentiment;

import edu.illinois.cs.cogcomp.lbjava.classify.*;
import edu.illinois.cs.cogcomp.lbjava.infer.*;
import edu.illinois.cs.cogcomp.lbjava.io.IOUtilities;
import edu.illinois.cs.cogcomp.lbjava.learn.*;
import edu.illinois.cs.cogcomp.lbjava.parse.*;
import edu.illinois.cs.cogcomp.sentiment.Document;
import edu.illinois.cs.cogcomp.sentiment.sentiment.Reader;
import java.util.List;


/** Negation Score feature */
public class NegationScore extends Classifier
{
  public NegationScore()
  {
    containingPackage = "edu.illinois.cs.cogcomp.sentiment.sentiment";
    name = "NegationScore";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.sentiment.Document"; }
  public String getOutputType() { return "discrete"; }


  public FeatureVector classify(Object __example)
  {
    return new FeatureVector(featureValue(__example));
  }

  public Feature featureValue(Object __example)
  {
    String result = discreteValue(__example);
    return new DiscretePrimitiveStringFeature(containingPackage, name, "", result, valueIndexOf(result), (short) allowableValues().length);
  }

  public String discreteValue(Object __example)
  {
    if (!(__example instanceof Document))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'NegationScore(Document)' defined on line 30 of SentimentClassifier.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    Document d = (Document) __example;

    String score = d.getNegationScore();
    return "" + (score);
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof Document[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'NegationScore(Document)' defined on line 30 of SentimentClassifier.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "NegationScore".hashCode(); }
  public boolean equals(Object o) { return o instanceof NegationScore; }
}

