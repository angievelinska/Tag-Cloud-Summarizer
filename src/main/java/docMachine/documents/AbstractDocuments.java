package docMachine.documents;

import com.coremedia.cap.content.Content;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * TODO: work more on search tree algo
 * 
 * User: avelinsk
 * Date: 10.09.2010
 */
public abstract class AbstractDocuments {
  private  final static Log log = LogFactory.getLog(AbstractDocuments.class);
  private List<Content> books;

    public final void iterate(Content rootElement){
      books = new ArrayList<Content>();

      getBooks(rootElement);
      log.info("No of books: "+books.size());

     for(Iterator<Content> iterator = books.iterator();iterator.hasNext();){
       Content book = iterator.next();
       List<Content> sections = book.getLinks("Sections");

       for (Iterator<Content> iterat = sections.iterator(); iterat.hasNext();){
         getTextsInSection(iterat.next());
       }
    }

  }

  // depth first search in the document tree
  public void getBooks(Content rootElement){
    Set<Content> children = rootElement.getChildren();

    if (rootElement.getType().getName().equals("Book")){
      books.add(rootElement);
    }

    for (Iterator<Content> iter = children.iterator(); iter.hasNext(); ){
      rootElement = iter.next();
      getBooks(rootElement);
    }
  }

  public void getTextsInSection(Content rootElement){
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

  public abstract void processText(Content content);

}
