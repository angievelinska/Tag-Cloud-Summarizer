package docMachine.documents;

import com.coremedia.cap.content.Content;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * User: avelinsk
 * Date: 10.09.2010
 */
public abstract class AbstractDocuments {

    public final void iterate(Content rootElement){
    //CMS_ONLINE
    Set<Content> children = rootElement.getChildren();

    // 5.1, 5.2, SoSo/SSE
    for(Iterator<Content> iter = children.iterator();iter.hasNext();){
      Content folder = iter.next();
      Set<Content> folders = folder.getChildren();

      for (Iterator<Content> it = folders.iterator();it.hasNext();){
        Content f = it.next();
        Set<Content> books = f.getChildrenWithType("Book");

        for(Iterator<Content> iterator = books.iterator();iterator.hasNext();){
          Content book = iterator.next();
          List<Content> sections = book.getLinks("Sections");

          for (Iterator<Content> iterat = sections.iterator(); iterat.hasNext();){

            getTextsForSection(iterat.next());
          }
        }
      }
    }
  }

  public abstract void getTextsForSection(Content section);
}
