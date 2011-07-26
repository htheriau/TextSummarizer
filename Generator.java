import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.*;

public class Generator{
  private InputDocument inputDoc;
  private double compressRatio = 0.2;
  private int blockDistance = 3;
  private String[] keywords = null;

  boolean cameraQuestion = false;
  boolean screenQuestion = false;
  boolean softwareQuestion = false;
  boolean cpuQuestion = false;
  String[] cameraWords = {"LED", "MP", "flash", "shooter", "megapixels", "video"};
  String[] screenWords = {"amoled", "pixels", "resolution", "sharp"};
  String[] softwareWords = {"android", "OS", "iOS", "version", "windows"};
  String[] cpuWords = {"mhz", "core", "ghz"};
  private TermCollection termCollection;

  public void loadFile(String inputFile){
    inputDoc = new InputDocument();
    inputDoc.loadFile(inputFile);
  }

  public String[] generateSynonyms(String[] keywords) {
    String synonyms = new String();
    String[] synonymList;
    List<String> mylist = new ArrayList<String>();
    for(String term : keywords){
      String wn = new String("./WordNet-3.0/bin/bin/wn " + term + " -synsn");
      //System.out.println(wn); //the command
      
      try{
        Process p = Runtime.getRuntime().exec(wn);
        BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
        
        while ((synonyms = output.readLine()) != null) {
          if ( synonyms.contains("Sense") ) {
             synonyms = output.readLine();
             //System.out.println(synonyms);
             synonymList = synonyms.split(", ");
             
             for(String word : synonymList) {
               mylist.add(word);
             }
             
             System.out.println("\nnumber of synonyms: " + synonymList.length);
             for (int z = 0; z < synonymList.length; z++) {
                System.out.print(synonymList[z] + " ");
             }
             
          }
        }
      } 
      catch (Exception e) {}
    }
    synonymList = mylist.toArray(new String[mylist.size()]);
    return synonymList;  }

  public void setKeywords(String[] keywords){
   
    
    List<String> processedTermList = new ArrayList<String>();
    TermPreprocessor tp = new TermPreprocessor();
    
    String[] synonyms = generateSynonyms(keywords);
    
    String resultTerm = null;
    for(String term : keywords){
      //look for keyword camera
      resultTerm = tp.preprocess(term);

      if(resultTerm !=null)
          if(resultTerm.equals("camera")){
	    cameraQuestion = true;
	    System.out.println("this is cam");
	  }
	  else if(resultTerm.equals("screen")){
	    screenQuestion = true;
	    System.out.println("this is cam");
	  }
	  else if(resultTerm.equals("software")){
	    softwareQuestion = true;
	    System.out.println("this is cam");
	  }
	  else if(resultTerm.equals("cpu")){
	    cpuQuestion = true;
	    System.out.println("this is cam");
	  }
        processedTermList.add(resultTerm);
    }
    for(String term : synonyms){
      resultTerm = tp.preprocess(term);

      if(resultTerm !=null)
        processedTermList.add(resultTerm);
    }

    this.keywords = processedTermList.toArray(new String[processedTermList.size()]);
    
    for(String blah : this.keywords) {
    	System.out.println(blah);
    }
  }

  public String generateSummary(){
    String[] significantSentences = generateSignificantSentences();
    StringBuilder builder = new StringBuilder();

    for(String sen : significantSentences){
      builder.append(sen).append('\n');
    }

    return StringTrimmer.trim(builder.toString(), '\n'); 

  }

  public String[] generateSignificantSentences(){
    String[] allSentences = getAllSentences();
    double[] scores = calcAllSentenceScores();
    String[] result = null;

    List<String> significantSentences = new ArrayList<String>();
    double senThreshold = calcSentenceThreshold();

    //System.out.println("sentenceThreshold = " + senThreshold);

    for(int i=0; i<allSentences.length; i++){
      if(scores[i] > 0){
        significantSentences.add(allSentences[i]);
        
        //System.out.println(allSentences[i]);
        //System.out.println(scores[i]);

        //System.out.println("sentence["+i+"]"+allSentences[i]);
        //System.out.println("score = "+scores[i]);
      }
    }

    result = significantSentences.toArray(new String[significantSentences.size()]);
    return result;
  }

  public double calcSentenceThreshold(){
    String[] allSentences = getAllSentences();
    float flen = Math.round(allSentences.length * compressRatio);
    int summaryLength = Math.round(flen);

    double[] scores = calcAllSentenceScores();
    Arrays.sort(scores);

    return scores[scores.length-summaryLength];
  }

  public String[] getAllSentences(){
    return inputDoc.getAllSentences();
  }

  public String[] generateSignificantTerms(){
    TermCollection allTerms;

    if(termCollection == null){
      TermCollectionProcessor tcp = new TermCollectionProcessor();
      tcp.insertAllTerms(inputDoc.getAllTerms());
      termCollection = tcp.getTermCollection();
    }

    int termThreshold = termCollection.getFrequencyValues()[5];

    //print out frequency values
    //for(int y=0; y<115; y++) {
      //System.out.print(termCollection.getFrequencyValues()[y] + " ");
    //}

    List<String> sigTerms = new ArrayList<String>();
    for(Word term : termCollection){
      if(term.getFrequency() >= termThreshold){
        System.out.print(term.getValue() + " ");
        sigTerms.add(term.getValue());
      }
      else
        break;
    }

    return sigTerms.toArray(new String[sigTerms.size()]);
  }

  public double[] calcAllSentenceScores(){
    String[] allSentences = getAllSentences();
    double[] scores = new double[allSentences.length];
    String[] significantTerms = generateSignificantTerms();
    
    for(int i=0; i<allSentences.length; i++){
      scores[i] = calcSentenceScore(significantTerms, allSentences[i]);
    }

    return scores;
  }

  public double calcSentenceScore(String[] significantTerms, String sentence){
    TextExtractor extractor = new TextExtractor();
    extractor.setText(sentence);
    String[] originalTerms = extractor.extractTerms();
    String[] processedTerms;
    List<String> processedTermList = new ArrayList<String>();


    //System.out.println("orginal Sentence:");
    for(String ot : originalTerms){
      //System.out.print(ot+" "); 
    }
    //System.out.println("");


    TermPreprocessor tp = new TermPreprocessor();
    String resultTerm = null;
    for(String term : originalTerms){
      resultTerm = tp.preprocess(term);

      if(resultTerm !=null)
        processedTermList.add(resultTerm);
    }



    processedTerms = processedTermList.toArray(new String[processedTermList.size()]);

    //System.out.print("sigTerm: ");
    for(String term : significantTerms){
      //System.out.print(term+" ");
    }
    //System.out.println("");

    //System.out.println("processed Sentence:");
    for(String pt : processedTerms){
      //System.out.print(pt+" ");
    }
    //System.out.println("");


    StringBuilder builder = new StringBuilder();
    for(String term : processedTerms){
      if(significantTerms != null && term != null){
        if(cameraQuestion == true ){
	  keywords = cameraWords;
        }
	if(screenQuestion == true){
	  keywords = screenWords;
	}
	if(softwareQuestion == true){
	  keywords = softwareWords;
	}
	if(cpuQuestion == true){
	  keywords = cpuWords;
	}
	
	if(contains(term, keywords)){
          builder.append('2');
        }
        else if(contains(term, significantTerms)){
          builder.append('1');
        }
        else{
          builder.append('0');
        }
      }
    }

    String symbolicSentence = builder.toString();

    System.out.println("symbolicSentence: "+symbolicSentence);

    return calcSymbolicSentenceScore(symbolicSentence);

  }

  private boolean contains(String str1, String[] strs){
    if(strs == null)
      return false;

    for(String st : strs){
      if(st.equals(str1)){
        return true;
      }
    }

    return false;
  }

  private double calcSymbolicSentenceScore(String sentence){
    String[] splits = sentence.split("000"); //block distance is 3 
  
    for(int i=0; i<splits.length; i++){
      splits[i] = StringTrimmer.trim(splits[i], '0');
      //System.out.println("split["+i+"]: "+splits[i]);
    }

    double score=0;
    double prevScore=0;
    for(String split : splits){
      if(split.length() != 0){
        int sigNum = StringTrimmer.count(split, '1');
        int keywordNum = StringTrimmer.count(split, '2');
        //int questionSpecific = StringTrimmer.count(split, '3'); 
        //prevScore = Math.pow((keywordNum*2+sigNum), 2)/split.length();
        //prevScore = 0.01 * Math.pow((keywordNum*2+sigNum), 2)/split.length();
        prevScore = keywordNum;
	score = Math.max(score, prevScore);   
        //score = keywordNum;     
      }
    }
    System.out.println("the score is " + score);
    return score;
  }

}
