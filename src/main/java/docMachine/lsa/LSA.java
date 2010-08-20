package docMachine.lsa;

import edu.ucla.sspace.common.ArgOptions;
import edu.ucla.sspace.lsa.LatentSemanticAnalysis;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

/**
 * User: avelinsk
 * Date: 11.08.2010
 */
public class LSA {
    Properties properties;

    public LatentSemanticAnalysis invokeLSA() throws IOException{
        loadProperties(initProperties());
        LatentSemanticAnalysis lsa = new LatentSemanticAnalysis(properties);

        //add all documents to the semantic space and call processSpace
        lsa.processSpace(properties);
        return lsa;
    }

    protected Hashtable initProperties(){
        Hashtable props = new Hashtable();
        props.put(LatentSemanticAnalysis.LSA_SVD_ALGORITHM_PROPERTY,"SVDLIBJ");
        props.put(LatentSemanticAnalysis.LSA_DIMENSIONS_PROPERTY,"300");
        props.put(LatentSemanticAnalysis.MATRIX_TRANSFORM_PROPERTY,"LogEntropyTransform");

        return props;
    }

    private void loadProperties(Hashtable props){
        properties = new Properties();
        properties.putAll(props);
    }

    private void initArgs(){
        ArgOptions args = new ArgOptions();
        String[] options = {"-d",
C:\master_thesis\SemanticSpaces\output\texts.txt
--tokenFilter
exclude=data\english-stop-words-large.txt
-w
true
-Z
edu.ucla.sspace.text.EnglishStemmer
-o
text
-S
SVDLIBJ
C:\master_thesis\SemanticSpaces\sspace-read-only\tools\LSA_text.sspace
-S
SVDLIBJ
-v};
        args.parseOptions();
    }
}

/*How can I convert a .sspace file to a matrix?
This question comes up also when a user wants to write the converted matrix to a file to interface with an external program, such as Matlab (e.g. to plot the vectors). An .sspace file can be converted as follows:

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