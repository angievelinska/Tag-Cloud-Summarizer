package docMachine.documents;

import com.coremedia.cap.content.Content;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author avelinsk
 */
public abstract class AbstractDocuments {
  private  final static Log log = LogFactory.getLog(AbstractDocuments.class);
  private List<Content> books;

  /**
   * Template method which retrieves the text elements
   * from documents' tree.
   *
   * @param rootElement
   */
    protected final void iterate(Content rootElement){
      books = new ArrayList<Content>();

      getBooks(rootElement);
      log.info("No of books: "+books.size());

      for(Content book : books){
        List<Content> sections = book.getLinks("Sections");

        for (Content element : sections){
          getTextsInSection(element);
        }
      }
    }

  /**
   * Depth first search in the documents' tree.
   */
  private void getBooks(Content rootElement){
    Set<Content> children = rootElement.getChildren();

    if (rootElement.getType().getName().equals("Book")){
      books.add(rootElement);
    }

    for (Content aChildren : children) {
      rootElement = aChildren;
      getBooks(rootElement);
    }
  }

  private void getTextsInSection(Content rootElement){
    if (rootElement.getType().getName().equals("Section")){
        for (Content article: rootElement.getLinks("Articles")){
            if (article.getType().getName().equals("MLArticle")){
               processText(article);
           }
        }
        if (rootElement.getLinks("Sections").size()>0){
          for (Content section:rootElement.getLinks("Sections")){
              getTextsInSection(section);
          }
        }
    } else if (rootElement.getType().getName().equals("MLArticle")){
        processText(rootElement);
    }
  }

  protected abstract void processText(Content content);

}
