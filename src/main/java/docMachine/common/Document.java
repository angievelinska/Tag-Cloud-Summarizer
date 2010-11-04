package docMachine.common;

import java.util.List;

/**
 * @author avelinsk
 */
public interface Document {

  public void labelDocument(List<Tag> tags);

  public double getTermFrequency(Tag word);
}
