package docMachine.documents;

import com.coremedia.cap.content.Content;

import java.util.Iterator;
import java.util.Set;

/**
 * User: avelinsk
 * Date: 02.08.2010
 */
public class Counter {
  private final static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Counter.class);
  private int counter = 0;

  public void count(Content rootElement){

    Set<Content>  children = rootElement.getChildren();
    log.info("Number of folders: "+children.size());
    Iterator<Content> iter = children.iterator();

    while(iter.hasNext()){
      Content folder = iter.next();
      Set<Content> folders = folder.getChildren();
      log.info("Number of folders: "+folders.size());

      for (Iterator<Content> it = folders.iterator();it.hasNext();){
        Content f = it.next();
        f.getLinks("Sections");
        Set<Content> sections = f.getChildrenWithType("Section");
        Iterator<Content> iterator = sections.iterator();

        while(iterator.hasNext()){
          Content section = iterator.next();
          getDocsInSection(section);
        }

      }
    }
  }

    public void getDocsInSection(Content rootElement){

     log.info("name of element: "+rootElement.getName());
     log.info("root element type is:  "+rootElement.getType().getName());

      if (rootElement.getType().getName().equals("Section")){

        for (Content article: rootElement.getLinks("Articles")){
           log.info("element type is:..."+article.getType().getName());
           log.info("name of element:"+article.getName());
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
    log.info("Number of Texts published as online documentation: "+counter);
    return counter;
  }

}
