package docMachine.parser;

import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: avelinsk
 * Date: 18.09.2010
 * Time: 15:55:50
 */
public class DocMachineParser {

  public void parseFiles(File dir, File outputFile){
    List files = listFiles(dir);

    if(!outputFile.exists()){
      outputFile = new File("output\\output.txt");
      try {
        outputFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    for (Iterator iter = files.iterator(); iter.hasNext();){
      File file = (File) iter.next();
        parseFile(file, outputFile);
    }
  }

    
  public List listFiles(File dir){

    if(dir.isDirectory()){
      File[] files = dir.listFiles();
      return Arrays.asList(files);

    } else {
        return new ArrayList();
    }
  }


  public void parseFile(File inputFile, File outputFile){
    Scanner scanner = null;
    StringBuilder text = null;
    try {
      scanner = new Scanner(inputFile);
      text = new StringBuilder();

      while(scanner.hasNextLine()){
        String line = scanner.nextLine();
        text.append(parseLine(line));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (scanner != null){
        scanner.close();
      }
    }

    BufferedWriter bufw = null;
    try {
      bufw = new BufferedWriter(new FileWriter(outputFile, true));
      bufw.write(text.toString());
      bufw.write(System.getProperty("line.separator"));

    } catch (IOException e) {
      e.printStackTrace();

    } finally {
      try {
        bufw.flush();
        bufw.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  //TODO: de-capitalize, remove delimiters, remove whitespaces and newlines
  public String parseLine(String line){
    StringBuilder parsedLine = new StringBuilder("");
    parsedLine.append(line);
    return parsedLine.toString().toLowerCase();
  }

}
