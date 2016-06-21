// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000B49CC2E4E2A4D294555580E4DCB29CCC5021EC93985C5C9996999A54A2A268A1E29F9C5A02155841D450B1D5507A4C47FF4B0FCF2A4926D15800FF06D150006994E15C14000000

package edu.illinois.cs.cogcomp.lbjava.sentiment;

import edu.illinois.cs.cogcomp.lbjava.Document;
import edu.illinois.cs.cogcomp.lbjava.classify.*;
import edu.illinois.cs.cogcomp.lbjava.infer.*;
import edu.illinois.cs.cogcomp.lbjava.io.IOUtilities;
import edu.illinois.cs.cogcomp.lbjava.learn.*;
import edu.illinois.cs.cogcomp.lbjava.parse.*;
import edu.illinois.cs.cogcomp.lbjava.sentiment.Reader;
import java.util.List;


public class SentimentClassifier$$1 extends Classifier
{
  private static final BagOfWords __BagOfWords = new BagOfWords();
  private static final POS __POS = new POS();

  public SentimentClassifier$$1()
  {
    containingPackage = "edu.illinois.cs.cogcomp.lbjava.sentiment";
    name = "SentimentClassifier$$1";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.lbjava.Document"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof Document))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'SentimentClassifier$$1(Document)' defined on line 26 of SentimentClassifier.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    FeatureVector __result;
    __result = new FeatureVector();
    __result.addFeatures(__BagOfWords.classify(__example));
    __result.addFeatures(__POS.classify(__example));
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof Document[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'SentimentClassifier$$1(Document)' defined on line 26 of SentimentClassifier.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "SentimentClassifier$$1".hashCode(); }
  public boolean equals(Object o) { return o instanceof SentimentClassifier$$1; }

  public java.util.LinkedList getCompositeChildren()
  {
    java.util.LinkedList result = new java.util.LinkedList();
    result.add(__BagOfWords);
    result.add(__POS);
    return result;
  }
}

