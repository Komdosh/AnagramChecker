# Anagram checker

## Task: determine if texts are anagrams

#### Clean solution

Transform texts to char arrays, sort char arrays, then compare it

```java
public class AnagramServiceSortImpl implements AnagramService {

    // Sort char array
    private String sort(final String text) {
        char[] textContent = text.toCharArray();
        Arrays.sort(textContent); //DualPivotQuicksort - worst case is O(n^2), average is O(n log n)
        return new String(textContent);
    }

    /**
     * Check two texts are anagram
     *
     * Time Complexity - O(n log n), worst case O(n^2) (because of sort)
     * Space Complexity - O(n)
     *
     * @param text1 - first text to check
     * @param text2 - second text to check
     * @param skipWhitespace - for use case, where we need to skip whitespaces
     * @return true if two texts are anagram
     */
    @Override
    public boolean checkAnagram(final String text1, final String text2, boolean skipWhitespace) {
        String first = text1;
        String second = text2;
        if (skipWhitespace) {
            first = text1.replaceAll(" ", "");
            second = text2.replaceAll(" ", "");
        }
        if (first.length() != second.length()) {
            return false;
        }

        return sort(first).equals(sort(second));
    }

    /**
     * Check list of texts are anagram
     *
     * Time Complexity - O(k*(n log n)), worst case O(n^2) (because of sort), where k - number of texts
     * Space Complexity - O(n)
     *
     * @param inputTexts - list of texts to check
     * @param skipWhitespaces - for use case, where we need to skip whitespaces
     * @return true if all texts are anagram
     */
    @Override
    public boolean checkAnagram(final List<String> inputTexts, boolean skipWhitespaces) {
        if (inputTexts.isEmpty()) {
            throw new IllegalArgumentException("List of inputTexts can't be empty");
        }
        if (inputTexts.size() < 2) {
            throw new IllegalArgumentException("There should be at least two inputTexts for anagram checking");
        }

        List<String> texts = inputTexts;
        if (skipWhitespaces) {
            texts = inputTexts.stream().map(text -> text.replaceAll(" ", "")).collect(Collectors.toList());
        }

        int firstTextSize = texts.get(0).length();
        boolean sizeNotEqual = texts.stream().skip(1).anyMatch(t -> t.length() != firstTextSize);

        if (sizeNotEqual) { // if sizes are different, then it is not an anagram
            return false;
        }

        final String sortedText = sort(texts.get(0));
        return texts.stream().skip(1).allMatch(t -> sortedText.equals(sort(t)));
    }
}
```

<hr/>

#### Optimized solution

Count symbols of every text to map. Compare symbol arrays.

```java
public class AnagramServiceImpl implements AnagramService {

    /**
     * Check two texts are anagram. Count number of first text symbols, then subtract symbols from second text,
     * if array contains non-zero elements, then texts are not anagram.
     *
     * Time Complexity - O(n)
     * Space Complexity - O(1) - we need space only for map of symbols
     *
     * @param text1          - first text to check
     * @param text2          - second text to check
     * @param skipWhitespace - for use case, where we need to skip whitespaces
     * @return true if two texts are anagram
     */
    @Override
    public boolean checkAnagram(final String text1, final String text2, boolean skipWhitespace) {
        String first = text1;
        String second = text2;
        if (skipWhitespace) {
            first = text1.replaceAll(" ", "");
            second = text2.replaceAll(" ", "");
        }
        if (first.length() != second.length()) {
            return false;
        }

        int[] chars = getSymbolMapCounter(second);

        for (int i = 0; i < first.length(); i++) {
            chars[first.charAt(i)]--;
            if (chars[first.charAt(i)] < 0) {
                return false; // first text contains less specified symbols, than second one
            }
        }

        return true; // first text contains same symbols as second
    }

    private boolean checkAnagram(final String second, int[] firstTextChars) {
        int[] chars = firstTextChars.clone();
        for (int i = 0; i < second.length(); i++) {
            chars[second.charAt(i)]--;
            if (chars[second.charAt(i)] < 0) {
                return false; // first text contains less specified symbols, than second one
            }
        }

        return true; // first text contains same symbols as second
    }

    /**
     * Check list of texts are anagram. Count number of first text symbols, then subtract symbols from next text,
     * if array contains non-zero elements, then texts are not anagram.
     *
     * Time Complexity - O(k*n), where k - number of texts
     * Space Complexity - O(n)
     *
     * @param inputTexts      - list of texts to check
     * @param skipWhitespaces - for use case, where we need to skip whitespaces
     * @return true if all texts are anagram
     */
    @Override
    public boolean checkAnagram(final List<String> inputTexts, boolean skipWhitespaces) {
        if (inputTexts.isEmpty()) {
            throw new IllegalArgumentException("List of inputTexts can't be empty");
        }
        if (inputTexts.size() < 2) {
            throw new IllegalArgumentException("There should be at least two inputTexts for anagram checking");
        }

        List<String> texts = inputTexts;
        if (skipWhitespaces) {
            texts = inputTexts.stream().map(text -> text.replaceAll(" ", "")).collect(Collectors.toList());
        }

        int firstTextSize = texts.get(0).length();
        boolean sizeNotEqual = texts.stream().skip(1).anyMatch(t -> t.length() != firstTextSize);

        if (sizeNotEqual) {
            return false;
        }

        String firstText = texts.get(0);

        int[] firstTextChars = getSymbolMapCounter(firstText);
        return texts.stream().skip(1).allMatch(t -> checkAnagram(t, firstTextChars));
    }

    private int[] getSymbolMapCounter(String text) {
        int size = text.toCharArray().length;
        // ASCII contains 128 symbols. Assume that number of unique symbols less than Integer.MAX_VALUE (~2^31)
        int[] chars = new int[128];
        for (int i = 0; i < size; i++) {
            chars[text.charAt(i)]++;
        }
        return chars;
    }
}
```
