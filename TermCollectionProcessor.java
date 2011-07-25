import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.io.IOException;

public class TermCollectionProcessor {


        private TermCollection termCollection;


        public TermCollectionProcessor() {
                termCollection = new TermCollection();
        }

        public TermCollection getTermCollection() {
                return termCollection;
        }

        public void sort() {
                List<Word> wordList = getTermCollection().getWordList();
                Word[] wordsArray = wordList.toArray(new Word[wordList.size()]);
                Arrays.sort(wordsArray);

                ListIterator<Word> i = wordList.listIterator();
                for (int j = 0; j < wordsArray.length; j++) {
                        i.next();
                        i.set(wordsArray[j]);
                }
        }

        public Word findWordByValue(String value) {
                List<Word> wordList = getTermCollection().getWordList();

                for (Word word : wordList) {
                        String strValue = word.getValue();
                        if (value.equals(strValue)) {
                                return word;
                        }
                }

                return null;
        }

        private void putInternal(String value) {
          if (value == null) {
                  return;
          }

          String preprocessValue = preprocess(value);
          
          if (preprocessValue == null) {
                  return;
          }

          Word existingWord = findWordByValue(preprocessValue);
          if (existingWord != null) {
                  existingWord.incrementFrequency();
                  return;
          }

          getTermCollection().getWordList().add(new Word(preprocessValue));

        }

        public String preprocess(String term){
          TermPreprocessor tp = new TermPreprocessor();
          String result = null;
          result = tp.preprocess(term);

          return result;
        }

        public void insertAllTerms(String[] values) {
                for (String value : values) {
                        putInternal(value);
                }
                sort();
                computeRelativeFrequencies();
        }

        public void computeRelativeFrequencies() {
                int totalFrequency = getTermCollection().getTotalFrequency();
                List<Word> wordList = getTermCollection().getWordList();
                for (Word word : wordList) {
                        int absoluteFreq = word.getFrequency();
                        double relativeFrequency = (double) absoluteFreq
                                        / (double) totalFrequency;
                        word.setRelativeFrequency(relativeFrequency);
                }
        }
}

