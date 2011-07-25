import java.util.Scanner;
import java.io.IOException;
import java.io.File;

public class InputDocument{

        protected String name;

        protected String content;

        public void loadFile(String filePath){
          try{
              Scanner scanner = new Scanner(new File(filePath));
              StringBuilder sb = new StringBuilder();

              while(scanner.hasNextLine()){
                sb.append(scanner.nextLine());
                if(scanner.hasNextLine()){
                  sb.append("\n");
                }
              }

              setContent(sb.toString());
          }
          catch(IOException e){
            System.out.println("InputDocument File IOException");
          }

        }

        public String getContent() {
                return content;
        }

        public void setContent(String content) {
                this.content = content;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String[] getAllTerms() {
                return createTextExtractor().extractTerms();
        }

        public String[] getAllSentences() {
                return createTextExtractor().extractSentences();
        }

        private TextExtractor createTextExtractor() {
                TextExtractor b = new TextExtractor();
                b.setText(getContent());
                return b;
        }

}

