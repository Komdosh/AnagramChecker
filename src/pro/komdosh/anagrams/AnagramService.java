package pro.komdosh.anagrams;

import java.util.List;

public interface AnagramService {

    boolean checkAnagram(final String first, final String second, boolean skipWhitespace);

    boolean checkAnagram(List<String> texts, boolean skipWhitespaces);
}
