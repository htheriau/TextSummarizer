import java.util.LinkedHashMap;
import java.util.Map;

public class Word implements Comparable<Word>{


        private String value;

        private int frequency = 1;

        private Double relativeFrequency;

        public Word() {
                super();
        }

        public Word(String value) {
                this.value = value;
        }

        public String getValue() {
                return value;
        }

        public void setValue(String value) {
                this.value = value;
        }
        
        public int getFrequency() {
                return frequency;
        }

        public int incrementFrequency() {
                return ++frequency;
        }

        public void setFrequency(int frequency) {
                this.frequency = frequency;
        }

        public Double getRelativeFrequency() {
                return relativeFrequency;
        }

        public void setRelativeFrequency(Double aRelativeFrequency) {
                relativeFrequency = aRelativeFrequency;
        }

        public int length() {
                return getValue().length();
        }

        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                final Word other = (Word) obj;
                if (value == null) {
                        if (other.value != null)
                                return false;
                } else if (!value.equals(other.value))
                        return false;
                return true;
        }

        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((value == null) ? 0 : value.hashCode());
                return result;
        }

        public int compareTo(Word otherWord) {
                int count1 = getFrequency();
                int count2 = otherWord.getFrequency();
                if (count1 < count2) {
                        return 1;

                } else if (count1 > count2) {
                        return -1;

                } else {
                        // ... If counts are equal, compare keys alphabetically.
                        return getValue().compareTo(otherWord.getValue());
                }
        }

}

