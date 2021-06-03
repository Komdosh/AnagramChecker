package pro.komdosh.anagrams;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractAnagramServiceImpl implements AnagramService {

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
        return texts.stream().skip(1).allMatch(t -> checkAnagram(firstText, t, skipWhitespaces));
    }
}
