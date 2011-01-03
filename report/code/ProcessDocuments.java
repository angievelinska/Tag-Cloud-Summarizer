public class ProcessDocuments extends AbstractDocuments{
  private static Logger log = Logger.getLogger(ProcessDocuments.class);
  private static final String path = "summarizer/data/output/";
  private static final String extension = ".txt"; 
 
  @Override
  protected void processText(Content article){
     String id = "";
     Markup markup = null;

     for (Content textElement : article.getLinks("Articles")){
      if (textElement.getType().getName().equals("Text")){
        List<Content> languages = textElement.getLinks("Language");
 
        if (languages.size()>0 &&
                languages.get(0).getString("Name").equalsIgnoreCase("en")){
          markup = textElement.getMarkup("Content");
          id = textElement.getId();
          serialize(id, markup);
        }
      }
    }
  }

   private void serialize(String id, Markup markup){
     StringBuffer sb = new  StringBuffer();
     sb.append(id.substring(id.lastIndexOf("/")+1));
     sb.append(extension);

     File outputPath = new File(path) ;
     if(!outputPath.exists()){
       outputPath.mkdir();
     }

     File outputFile = new File(sb.toString());
     if (!outputFile.exists()){
       outputFile = new File(outputPath,sb.toString());
     }

     try {
       BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
       String plainText = asPlainText(markup);
       writer.write(plainText);
       writer.close();

     } catch (IOException ex){
       log.error(ex);
     }
     log.info("*** File created: "+sb.toString());
   }

  private String asPlainText(Markup markup){
    if (markup ==  null) return "";

    StringWriter writer = new StringWriter();
    PlaintextSerializer serializer = new PlaintextSerializer(writer);
    markup.writeOn(serializer);

    return writer.toString();
  }
}