package pro.komdosh.anagrams;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
     * @param caseInsensitive - for use case, where we need to check with case insensitive
     * @return true if two texts are anagram
     */
    @Override
    public boolean checkAnagram(final String text1, final String text2, boolean skipWhitespace, boolean caseInsensitive) {
        String first = text1;
        String second = text2;
        if (skipWhitespace) {
            first = text1.replaceAll(" ", "");
            second = text2.replaceAll(" ", "");
        }
        if (caseInsensitive) {
            first = first.toLowerCase(Locale.ROOT);
            second = second.toLowerCase(Locale.ROOT);
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
     * @param caseInsensitive - for use case, where we need to check with case insensitive
     * @return true if all texts are anagram
     */
    @Override
    public boolean checkAnagram(final List<String> inputTexts, boolean skipWhitespaces, boolean caseInsensitive) {
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
        if (caseInsensitive) {
            texts = texts.stream().map(text -> text.toLowerCase(Locale.ROOT)).collect(Collectors.toList());
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
