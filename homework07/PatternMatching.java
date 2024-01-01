import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various pattern matching algorithms.
 *
 * @author LAUREN SABO
 * @version 1.0
 * @userid lsabo8
 * @GTID 903669960
 * Collaborators: n/a
 *
 * Resources: n/a
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch and shifting down by 1.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> bruteForce(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is null or pattern.length() is 0");
        } else if (text == null || text.length() == 0) {
            throw new IllegalArgumentException("text parameter is null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("comparator parameter is null.");
        }

        List<Integer> list = new ArrayList<>();
        int tLen = text.length();
        int pLen = pattern.length();
        int i;
        for (i = 0; i < tLen - pLen + 1; i++) {
            for (int j = 0; j < pLen; j++) {
                if (comparator.compare(text.charAt(i + j), pattern.charAt(j)) != 0) {
                    break;
                }
                if (j == pLen - 1) {
                    list.add(i);
                    break;
                }
            }
        }
        return list;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     * The table built should be the length of the input text.
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     * Ex. ababac
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("comparator is null");
        }
        int[] fTable = new int[pattern.length()];
        if (fTable.length != 0) {
            int i = 0;
            int j = 1;
            int m = fTable.length;
            fTable[0] = 0;
            while (j < m) {
                if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                    fTable[j] = i + 1;
                    i++;
                    j++;
                } else {
                    if (i == 0) {
                        fTable[j] = 0;
                        j++;
                    } else {
                        i = fTable[i - 1];
                    }
                }
            }
        }
        return fTable;
    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     * Make sure to implement the failure table before implementing this
     * method. The amount to shift by upon a mismatch will depend on this table.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is null");
        } else if (text == null) {
            throw new IllegalArgumentException("text is null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("comparator is null");
        }
        List<Integer> list = new ArrayList<>();
        int i = 0;
        int j = 0;
        int m = pattern.length();
        int n = text.length();
        if (m <= n) {
            int[] fTable = buildFailureTable(pattern, comparator);
            while (i <= n - m) {
                while (j < m && comparator.compare(pattern.charAt(j),
                        text.charAt(i + j)) == 0) {
                    j++;
                }
                if (j == 0) {
                    i++;
                } else {
                    if (j == m) {
                        list.add(i);
                    }
                    int step = fTable[j - 1];
                    i = i + j - step;
                    j = step;
                }
            }
        }
        return list;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     * Ex. octocat
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        }
        Map<Character, Integer> lTable = new HashMap<>();
        if (pattern.length() != 0) {
            for (int i = 0; i < pattern.length(); i++) {
                Character charA = pattern.charAt(i);
                lTable.put(charA, i);
            }
        }
        return lTable;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is null");
        } else if (text == null || text.length() == 0) {
            throw new IllegalArgumentException("text is null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("comparator is null");
        }
        List<Integer> list = new ArrayList<>();
        int m = pattern.length();
        int n = text.length();
        if (m <= n) {
            Map<Character, Integer> lastOccur = buildLastTable(pattern);
            int i = 0;
            while (i <= n - m) {
                int j = pattern.length() - 1;
                while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                    j--;
                }
                if (j == -1) {
                    list.add(i);
                    i++;
                } else {
                    Integer step = lastOccur.get(text.charAt(i + j));
                    if (step == null) {
                        step = -1;
                    }
                    if (step < j) {
                        i += (j - step);
                    } else {
                        i++;
                    }
                }
            }
        }
        return list;
    }

    /**
     * This method is OPTIONAL and for extra credit only.
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                                    CharSequence text,
                                                    CharacterComparator comparator) {
        return null; // if you are not implementing this method, do NOT modify this line
    }
}
