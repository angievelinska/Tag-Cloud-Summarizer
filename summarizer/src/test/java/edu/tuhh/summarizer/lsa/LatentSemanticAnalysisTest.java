package edu.tuhh.summarizer.lsa;

import edu.tuhh.summarizer.common.Document;
import edu.tuhh.summarizer.common.DocumentImpl;
import edu.ucla.sspace.common.DocumentVectorBuilder;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.common.Similarity;
import edu.ucla.sspace.lsa.LatentSemanticAnalysis;
import edu.ucla.sspace.text.IteratorFactory;
import edu.ucla.sspace.vector.DenseVector;
import edu.ucla.sspace.vector.DoubleVector;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * @author avelinsk
 */
public class LatentSemanticAnalysisTest {
  LatentSemanticAnalysis lsa;
  List<Document> documents;

  @Test
  public void testLSA(){

    documents = getDocuments();

    try{

      lsa = new LatentSemanticAnalysis();
      for (int i = 0; i<documents.size(); i++){
        lsa.processDocument(documents.get(i).reader());
      }

      lsa.processSpace(getProps());

    }catch(IOException e){
      e.printStackTrace();
    }

    DocumentVectorBuilder docBuilder = new DocumentVectorBuilder(lsa);

    String q = "typographic database system";

    DoubleVector query = docBuilder.buildVector(new BufferedReader(new StringReader(q)),new DenseVector(lsa.getVectorLength()));

    Map<DoubleVector, Double> searchResults = new HashMap<DoubleVector, Double>();

    for (int i=0; i<9; i++){
      DoubleVector doc = lsa.getDocumentVector(i);

      double cosim = Similarity.getSimilarity(Similarity.SimType.COSINE, doc.toArray(), query.toArray());

      searchResults.put(doc, cosim);

      System.out.println("Similarity with document id: "+ (i+1) + "is : " +cosim);

    }


    File sspace = new File("lsa.sspace");
    try{
      sspace.createNewFile();
      SemanticSpaceIO.save(lsa, sspace );
    }catch(IOException e){
      e.printStackTrace();
    }

    Map<Integer,DoubleVector> docs = lsa.getDocuments();
    for (Map.Entry<Integer,DoubleVector> entry : docs.entrySet()){
      
    }

  }

  public List<Document> getDocuments(){
      String[] documents = {
                    "new component names since version       as product and component names underwent a thorough renaming procedure  new and old names might appear simultaneously within this manual  we try to adapt the new nomenclature as consistent as possible  but as old component names shine through in the software itself  we stick to these in case of doubt",
                    "in addition  these symbols can mark single paragraphs    pictograph description   tip  this denotes a best practice or a recommendation    warning  please pay special attention to the text    danger  the violation of these rules causes severe damage    summary  this symbol indicates a summary of the above text",
                     "element typographic format example   source code   command line entries  parameter and values courier new cm httpd start menu names and entries bold  linked with      open the menu entry  format normal   field names  coremedia components italic   enter in the field heading  the coremedia component entries in quotation marks enter  on   simultaneously  pressed keys bracketed in       linked with     press the keys  ctrl   a  emphasis italic it is not saved buttons bold  with square brackets click on the  ok  button glossary entry    shaped icon    webdav code lines in code examples which continue in the next line      cm contentserver    start",
                    "this manual addresses everyone involved in using the social software extension   whether you are a manager who wants to learn about the concepts behind the terms web     and enterprise       an administrator who has the task of installing  configuring and operating the social software extension  or the developer who is responsible for the integration of social software functionality into existing and new web applications  this guide is for you",
                    "currently  everything seems to be      web      enterprise      and there s even  life      with the virtual world offered by  second life   but what is all this hype really about   if you follow tim o reilly   who coined the term  web      in        the  new web  is not so much dependent on a new technology but on a new attitude  web     therefore has no clearly defined set of technological requirements  but is rather a description of a fundamentally new way to use the world wide web   on the new web  all users are active members  they interact instead of consuming passively  users build networks  make contacts  discuss  create  and comment  they are able to interact in real time  world wide and in such numbers that the sheer mass of active members  users  creates its own dynamic   understood in this sense  one may interpret the new  web      as the renaissance of the original idea of an interactive web  just as it was intended at the time the web was invented in the early   s  every user of the system was both reader and author  with the software supporting both the reading and writing of content directly  onto  the web  however  the great success of the mosaic browser  which introduced graphics but possessed no editing functionality  lead to the concept of  writing the web  being neglected for many years   web     is thus in a nutshell the rebirth of the  read write web    as tim berners lee  the web s inventor  has often referred to its ideal form   and can be summarized as exhibiting the following key characteristics     active participation of users   users create content users rate  comment and evaluate users create taxonomies by using tagging  or similar methods    users interact  creating  collective wisdom    making contact co authoring content sharing content with other users   a highly interactive experience   lightweight  easy to use web applications rich  feature packed  thin  light weight  clients  utilizing technologies such as ajax many require nothing more than a web browser to run   services are  open    open apis for creating  mashups   the web viewed as a platform easy syndication of content via rss and search engines many ways to access content  human readable urls  shorter urls  permanent urls   service   rather than product   oriented approach   always beta frequent updates quick launch of new services  web     is the attitude that makes key cutting edge concepts such as the  wisdom of crowds  or the  long tail  truly work as business models   coremedia social software extension gives you the tools to take this attitude to your web presence",
                    "an increasing number of web applications and websites depend on their users to create and modify a large proportion of their content   so called  user generated content   ugc  being the result  indeed  many sites also allow their users to contribute to the existing  official  editorial content by submitting reviews  comments  additional material or categorizations  or by getting involved in other ways  the types of applications with such user involvement range from traditional websites enhanced by simple commenting mechanisms to portals basing their entire business model purely on ugc  content sharing  and user collaboration  one of the differences between traditional websites and those involving a significant amount of ugc is the greatly increased writer reader ratio  the coremedia social software extension  coremedia sse  provides application developers with a data store and application programming interfaces  api  to support the creation of such sites as described above",
                    "coremedia sse components use databases to store data and therefore require a database system to be available  this database system should be fully installed and available before beginning installation and configuration of coremedia sse  component specific configuration information is given in the configuration chapter below   the table below lists the certified database systems for coremedia sse",
                    "in this chapter you will find a table with all major changes made in this manual",
                    "this chapter lists the system requirements for the coremedia social software extension"
      };

    List<Document> docs = new ArrayList<Document>();

    for (int i=0; i<documents.length; i++){
      edu.tuhh.summarizer.common.Document doc = new DocumentImpl(i, documents[i]);
      docs.add(doc);
    }

    return docs;
  }

  public Properties getProps(){
    Properties props = System.getProperties();
    props.put(LatentSemanticAnalysis.LSA_DIMENSIONS_PROPERTY,"7");
    props.put(LatentSemanticAnalysis.LSA_SVD_ALGORITHM_PROPERTY, "SVDLIBJ");
    props.put(LatentSemanticAnalysis.RETAIN_DOCUMENT_SPACE_PROPERTY, "true");
    props.put("outputFormat", SemanticSpaceIO.SSpaceFormat.TEXT);
    props.put("overwrite","true");
    props.put("verbose","true");
    IteratorFactory.setProperties(props);

    return props;
  }


}

