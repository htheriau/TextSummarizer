import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

public class TermPreprocessor{

  public String preprocess(String term){
    String resultTerm = null;
    if(term != null)
      resultTerm = convertToLowerCase(term);
    if(resultTerm != null)
      resultTerm = removePunctuations(resultTerm);
    if(resultTerm != null){
      try{
        resultTerm = removeStopWords(resultTerm);
      }
      catch(IOException e){
        System.out.println("TermPreprocessor IOException");
      }
    }
    if(resultTerm != null)
      resultTerm = stemming(resultTerm);

    return resultTerm;
  }

  public String convertToLowerCase(String term){
    return term.toLowerCase();
  }

  public String removePunctuations(String term){
    char[] chars = term.toCharArray();
    int length = chars.length;

    int count = 0;
    for(char ch : chars){
      if(!Character.isLetterOrDigit(ch)){
        count++;
      }
    }

    if(count==length)
      return null;
    else
      return term;
  }

  public String removeStopWords(String term) throws IOException{
    String filePath = "./stopwords.txt";

    Scanner scanner = new Scanner(new File(filePath)); 

    List<String> stopWordList = new ArrayList<String>();

    while(scanner.hasNextLine()){
      String line = scanner.nextLine().trim();

      if(line.isEmpty()){
        continue;
      }

      if(line.startsWith("|")){
        continue;
      }

      int firstCommentIndex = line.indexOf("|");
      if(firstCommentIndex != -1){
        line = line.substring(0, firstCommentIndex).trim();
      }

      stopWordList.add(line);
    }

    if(stopWordList.contains(term)){
      return null;
    }
    else{
      return term;
    }
  }

  public String stemming(String term){
    Stemmer stemmer = new Stemmer();
    term = term.trim();
    stemmer.add(term.toCharArray(), term.length());
    stemmer.stem();
    return stemmer.toString();
  }

}
