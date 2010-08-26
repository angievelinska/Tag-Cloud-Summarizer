package docMachine.lsa;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.lsa.LatentSemanticAnalysis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

/**
 * User: avelinsk
 * Date: 11.08.2010
 *
 * The class creates a Semantic Space using Latent Semantic Analysis (LSA) algorithm.
 */
public class LSA {
    private final static Log log = LogFactory.getLog(LSA.class);

    protected LatentSemanticAnalysis invokeLSA() throws IOException{
        //add all documents to the semantic space and call processSpace
        Properties props = new Properties();
        props.putAll(initProperties());
        LatentSemanticAnalysis lsa = new LatentSemanticAnalysis(props);
//        this.parseProps();
        return lsa;
    }

    protected Hashtable initProperties(){
/*
        args = new ArgOptions();
        String[] options = {"-d",
                            "C:\\master_thesis\\TagCloudSummarizer\\output\\input.txt",
                            "--tokenFilter",
                            "exclude=stopwords\\english-stop-words-large.txt",
                            "-w",
                            "true",
                            "-Z",
                            "edu.ucla.sspace.txt.EnglishStemmer",
                            "-o",
                            "text",
                            "-S",
                            "SVDLIBJ",
                            "C:\\master_thesis\\TagCloudSummarizer\\output\\LSA_text.sspace"};
        args.parseOptions(options);
*/

        Hashtable props = new Hashtable();
        props.put("docFile","C:\\master_thesis\\TagCloudSummarizer\\input\\input.txt");
        props.put(LatentSemanticAnalysis.LSA_SVD_ALGORITHM_PROPERTY,"SVDLIBJ");
        props.put(LatentSemanticAnalysis.LSA_DIMENSIONS_PROPERTY,"300");
        props.put(LatentSemanticAnalysis.MATRIX_TRANSFORM_PROPERTY,"edu.ucla.sspace.matrix.LogEntropyTransform");
        props.put("outputFormat", SemanticSpaceIO.SSpaceFormat.TEXT);
        props.put("overwrite","true");
        props.put("tokenFilter","exclude=C:\\master_thesis\\TagCloudSummarizer\\stopwords\\english-stop-words-large.txt");
        props.put("verbose","true");

        return props;
    }

    protected File initOutputFile() throws IOException{
        File outputPath = new File("C:\\master_thesis\\TagCloudSummarizer\\sspace");
        File outputFile = new File(outputPath,"LSA.sspace");
        if (!outputFile.exists()){
            outputFile.createNewFile();
        }
        return outputFile;
    }

    public void runLSA() throws IOException{
        long start = System.currentTimeMillis();
        SemanticSpace sspace = this.invokeLSA();
        File output = this.initOutputFile();
        DataInputStream dis= new DataInputStream(new BufferedInputStream(new FileInputStream(output)));
        SemanticSpaceIO.save(sspace, output, SemanticSpaceIO.SSpaceFormat.TEXT);
        long end = System.currentTimeMillis();
        log.info("-------------INFO---------------");
        log.info("LSA used "+(end-start)+"ms to index the document collection.");
        log.info("Name of semantic algorithm:"+sspace.getSpaceName());
        log.info("Number of words in the sspace: "+sspace.getWords().size());
        this.parseProps();
        log.info("-------------END----------------");
    }

    protected void parseDocuments(){
        Properties props = System.getProperties();
        Iterator it = props.stringPropertyNames().iterator();
/*        for (Map.Entry st : props){

        }
                .next().equalsIgnoreCase()
        String docFile =  props.getProperty("docFile");
        log.info("docFile: "+docFie);
        Iterator<Document> docIter =*/ 
    }

    protected void parseProps(){
        Properties sysprops = System.getProperties();
        log.info("Properties:");
        for(Enumeration en = sysprops.propertyNames(); en.hasMoreElements(); ){
            String key = (String) en.nextElement();
            String value = sysprops.getProperty(key);
            log.info(key+" = "+value);
        }
    }

}

/*How can I convert a .sspace file to a matrix?
This question comes up also when a user wants to write the converted matrix to a file to interface
with an external program, such as Matlab (e.g. to plot the vectors). An .sspace file can be converted as follows:

SemanticSpace sspace = null; // Wherever you get it from
int numVectors = sspace.getWords().size();
DoubleVector[] vectors = new DoubleVector[numVectors];
String[] words = new String[numWords];
int i = 0;
for (String word : sspace.getWords()) {
    words[i] = word;
    vectors[i] = Vectors.asDouble(sspace.getVector(word));
    i++;
}

Matrix m = Matrices.asMatrix(Arrays.asList(vectors));

// If you then want to write the matrix out to disk, do the following
MatrixIO.Format fmt = null;   // specify here
File outputMatrixFile = null; // also specify
MatrixIO.writeMatrix(m, outputMatrixFile, fmt);
// Reminder, if you still want the word-to-row mapping, write out the words array too
*/