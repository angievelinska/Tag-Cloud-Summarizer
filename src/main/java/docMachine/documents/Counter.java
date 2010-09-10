package docMachine.documents;

import com.coremedia.cap.content.Content;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * User: avelinsk
 * Date: 02.08.2010
 *
 * TODO: refactor
 */
public class Counter {
  private final static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Counter.class);
  private int counter = 0;

  public void count(Content rootElement){
    //CMS_ONLINE
    Set<Content>  children = rootElement.getChildren();

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

            getDocsInSection(iterat.next());
          }
        }
      }
    }
  }

    public void getDocsInSection(Content rootElement){
    if (rootElement.getType().getName().equals("Section")){

        for (Content article: rootElement.getLinks("Articles")){
            if (article.getType().getName().equals("MLArticle")){

               counter++;
           }
        }

        if (rootElement.getLinks("Sections").size()>0){
          for (Content section:rootElement.getLinks("Sections")){

              getDocsInSection(section);
          }
        }

    } else if (rootElement.getType().getName().equals("MLArticle")){

        counter++;
    }
  }

  public int getCount(){
    log.info("Number of sections/texts published as online documentation: "+counter);
    
    return counter;
  }

}
