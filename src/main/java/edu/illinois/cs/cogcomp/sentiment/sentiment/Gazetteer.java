// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000B49CC2E4E2A4D2945507F4CAA4D29294D4D22D079CF4E2DCD4DCB215841D450B1D558A6580E292ACCCB47582E4ECF2A4550B55841DB4F4D29060174343DA5108A9B4B82F02296DA05B00510357C905000000

package edu.illinois.cs.cogcomp.sentiment.sentiment;

import edu.illinois.cs.cogcomp.lbjava.classify.*;
import edu.illinois.cs.cogcomp.lbjava.infer.*;
import edu.illinois.cs.cogcomp.lbjava.io.IOUtilities;
import edu.illinois.cs.cogcomp.lbjava.learn.*;
import edu.illinois.cs.cogcomp.lbjava.parse.*;
import edu.illinois.cs.cogcomp.sentiment.Document;
import edu.illinois.cs.cogcomp.sentiment.sentiment.Reader;
import java.util.List;


/** Gazetteer feature */
public class Gazetteer extends Classifier
{
  public Gazetteer()
  {
    containingPackage = "edu.illinois.cs.cogcomp.sentiment.sentiment";
    name = "Gazetteer";
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
      System.err.println("Classifier 'Gazetteer(Document)' defined on line 24 of SentimentClassifier.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    Document d = (Document) __example;

    String score = d.getScore();
    return "" + (score);
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof Document[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'Gazetteer(Document)' defined on line 24 of SentimentClassifier.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "Gazetteer".hashCode(); }
  public boolean equals(Object o) { return o instanceof Gazetteer; }
}

