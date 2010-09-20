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
    List<File> files = listFiles(dir);
    System.out.println("number of files: "+files.size());

    if (!dir.exists()){

      //if no documents to parse, end
      return;
    }
    
    if(!outputFile.exists()){
      try {
        outputFile.createNewFile();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }

    for (File f : files){
      parseFile(f, outputFile);
    }
  }

  
  public List<File> listFiles(File dir){
    if(dir.isDirectory()){
      File[] files = dir.listFiles();
      return Arrays.asList(files);

    } else {
        return new ArrayList<File>();
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
      } catch (NullPointerException ex){
        ex.printStackTrace();
      }
    }
  }


  public String parseLine(String line){
    StringBuilder parsedLine = new StringBuilder("");
    line = line.trim();
    // escape all non-letter characters:
    line=line.replaceAll("[^a-zA-Z]"," ");
    parsedLine.append(line);
    parsedLine.append(" ");
    
    return parsedLine.toString().toLowerCase();
  }
}

