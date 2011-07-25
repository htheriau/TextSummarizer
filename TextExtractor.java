import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TextExtractor {

        private String text;

        public String getText() {
                return text;
        }

        public void setText(String text) {
                this.text = text;
        }

        public String[] extractTerms() {
                BreakIterator boundary = BreakIterator.getWordInstance(Locale.US);
                boundary.setText(getText());

                List<String> words = new ArrayList<String>();
                int start = boundary.first();
                int end = boundary.next();
                while (end != BreakIterator.DONE) {
                        String word = getText().substring(start, end).trim();
                        if (!word.isEmpty()) {
                                words.add(word);
                        }
                        start = end;
                        end = boundary.next();
                }

                return words.toArray(new String[words.size()]);
        }

        public String[] extractSentences() {
                BreakIterator boundary = BreakIterator.getSentenceInstance(Locale.US);
                boundary.setText(getText());

                List<String> sentences = new ArrayList<String>();
                int start = boundary.first();
                int end = boundary.next();
                while (end != BreakIterator.DONE) {
                        String sentence = getText().substring(start, end).trim();
                        if (!sentence.isEmpty()) {
                                sentences.add(sentence);
                        }
                        start = end;
                        end = boundary.next();
                }

                return sentences.toArray(new String[sentences.size()]);
        }

}

