package docMachine.lsa;

import edu.ucla.sspace.common.ArgOptions;
import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.lsa.LatentSemanticAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

/**
 * User: avelinsk
 * Date: 11.08.2010
 *
 * Class creates a Semantic Space using Latent Semantic Analysis (LSA) algo.
 */
public class LSA {
    protected ArgOptions args;

    public LatentSemanticAnalysis invokeLSA() throws IOException{
/*        String[] options = {"-d",
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
                    "C:\\master_thesis\\TagCloudSummarizer\\output\\LSA_text.sspace"};*/


        //add all documents to the semantic space and call processSpace
        Properties props = new Properties();
        props.putAll(initProperties());
        LatentSemanticAnalysis lsa = new LatentSemanticAnalysis(props);
//        lsa.processSpace(props);

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
        props.put("tokenFilter","exclude=data\\english-stop-words-large.txt");
        props.put("verbose","true");
        props.put("","");

        return props;
    }

    protected File initOutputFile(){
        File outputPath = new File("C:\\master_thesis\\TagCloudSummarizer\\sspace");
        File outputFile = new File(outputPath,"LSA.sspace");

        return outputFile;
    }

    public void runLSA() throws IOException{
        SemanticSpace sspace = this.invokeLSA();
        File output = this.initOutputFile();
        SemanticSpaceIO.save(sspace, output);
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