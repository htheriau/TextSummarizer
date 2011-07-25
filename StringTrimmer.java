import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class StringTrimmer {

        /**
         * Create a line
         *
         * @param c
         *            The fill char
         * @param l
         *            The line lenght
         * @return The text line
         */
        static public String line(char c, int l) {
                char[] chars = new char[l];
                Arrays.fill(chars, c);

                return new String(chars);
        }

        /**
         * Count the occurrences of a given char in a string
         *
         * @param s
         *            The char to be counted
         * @param c
         *            The string to be examinde
         * @return The occurrences of a given char in a string
         */
        static public int count(String s, char c) {
                int count = 0;

                char[] chars = s.toCharArray();
                for (char tempC : chars) {
                        if (tempC == c) {
                                count++;
                        }
                }

                return count;
        }

        /**
         * Returns a copy of the string, with leading and trailing char omitted.
         *
         * @param s
         *            The string to trim
         * @param c
         *            The char to be deleted
         * @return The trimmed string
         */
        static public String trim(String s, char c) {
                String result = trimLeft(s, c);
                result = trimRight(result, c);

                return result;
        }

        public static String trimLeft(String s, char c) {
                int len = s.length();
                int st = 0;
                char[] val = s.toCharArray();

                // Trim left
                while ((st < len) && (val[st] == c)) {
                        st++;
                }

                return ((st > 0) || (len < s.length())) ? s.substring(st, len) : s;
        }

        public static String trimRight(String s, char c) {
                int len = s.length();
                int st = 0;
                char[] val = s.toCharArray();

                while ((st < len) && (val[len - 1] == c)) {
                        len--;
                }

                return ((st > 0) || (len < s.length())) ? s.substring(st, len) : s;
        }

        /**
         * Is a String?
         *
         * @param str
         * @return <tt>true</tt> is alphanumeric
         */
        public static boolean isString(String str) {
                try {
                        Double.parseDouble(str);
                        return false;
                } catch (NumberFormatException exc) {
                        return true;
                }
        }

        /**
         * Load a text file in CharSequence
         *
         * @param file
         *            The file to load
         * @return The text content of the file as {@link CharSequence}
         * @throws FileNotFoundException
         * @throws IOException
         */
        static public CharSequence load(File source, String charsetName)
                        throws FileNotFoundException, IOException {
                StringBuilder contents = new StringBuilder();

                Scanner scanner = new Scanner(source, charsetName);

                try {
                        while (scanner.hasNextLine()) {
                                String line = scanner.nextLine();
                                contents.append(line);
                                if (scanner.hasNextLine()) {
                                        contents.append("\n");
                                }
                        }
                } finally {
                        scanner.close();
                }

                return contents;

        }
}

