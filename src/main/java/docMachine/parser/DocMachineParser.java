package docMachine.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: avelinsk
 * Date: 18.09.2010
 * Time: 15:55:50
 */
public class DocMachineParser {

  public void parse(String txt){
    String regExp = "[ .,:]";
    String[] tokens = txt.split(regExp) ;
  }

  public List listFiles(File dir){
    if(dir.isDirectory()){
      File[] files = dir.listFiles();
      return Arrays.asList(files);
    }else {
      return new ArrayList();
    }
  }
}
