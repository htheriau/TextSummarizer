public class Main{
  public static void main(String[] args){
    if(args.length < 1){
      System.out.println("Usage: java Main input.txt keywords ...");
      return;
    }
    String filePath = args[0];

    if(filePath == null){
      filePath = "./input.txt";
    }

    String[] keywords = null; 
    if(args.length < 2){
      keywords = new String[1];
      keywords[0] = "";
    }
    else{
      keywords = new String[args.length-1];
      for(int i=1; i<args.length; i++){
        keywords[i-1] = args[i];
      }
    }

    System.out.print("keywords:\t");
    for(String keyword : keywords){
      System.out.print(keyword+"\t");
      System.out.println();
    }

    //String[] keywords = null; 
    Generator generator = new Generator();

    generator.loadFile(filePath);
    generator.setKeywords(keywords);
//    generator.generateSignificantSentences();
    System.out.println(generator.generateSummary());
//    generator.generateSummary();
  }
}
