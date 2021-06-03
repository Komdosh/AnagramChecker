package pro.komdosh.anagrams;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnagramServiceImplTest {

    private final AnagramService anagramService = new AnagramServiceImpl();
//    private final AnagramService anagramService = new AnagramServiceSortImpl();

    @Test
    void checkAnagramTestTwoTextsTrue() {
        String first = "Germany has beautiful nature";
        String second = "many Geurer hs beautla natifu";

        assertTrue(anagramService.checkAnagram(first, second, true));
    }

    @Test
    void checkAnagramTestTwoTextsFalse() {
        String first = "Germany has beautiful nature";
        String second = "many Gedrer hs beautla natifu";

        assertFalse(anagramService.checkAnagram(first, second, true));
    }

    @Test
    void checkAnagramTestTwoTextsTrueWithWhitespaces() {
        String first = "Germany has beautiful nature";
        String second = "many Geurer hs beautlanatifu";

        assertTrue(anagramService.checkAnagram(first, second, true));
    }

    @Test
    void checkAnagramTestTwoTextsTrueWithWhitespacesFalse() {
        String first = "Germany has beautiful nature";
        String second = "many Geurer hs beautla nati fu";

        assertFalse(anagramService.checkAnagram(first, second, false));
    }

    @Test
    void checkAnagramTesDifferentLength() {
        String first = "Germany has beautiful nature";
        String second = "Germany has beautiful nature.";

        assertFalse(anagramService.checkAnagram(first, second, true));
    }

    @Test
    void checkAnagramTestSkipWhitespacesTrue() {
        String first = "Germany has beautiful nature";
        String second = "many Geurer hs beautla natifu";

        assertTrue(anagramService.checkAnagram(first, second, true));
    }

    @Test
    void checkAnagramTestManyTexts() {
        List<String> texts = List.of(
            "Germany has beautiful nature",
            "many Geurer hs beautla natifu",
            "autmany elrer hs ba natiGeufu"
        );

        assertTrue(anagramService.checkAnagram(texts, true));
    }

    @Test
    void checkAnagramTestDifferentSizesSameSymbols() {
        List<String> texts = List.of(
            "1",
            "11"
        );

        assertFalse(anagramService.checkAnagram(texts, true));
    }

    @Test
    void checkAnagramTestListEmptyThrowsException() {
        List<String> texts = List.of();

        assertThrows(IllegalArgumentException.class,()-> anagramService.checkAnagram(texts, true));
    }

    @Test
    void checkAnagramTestListHasOneTextThrowsException() {
        List<String> texts = List.of(
            "Only one text throws"
        );

        assertThrows(IllegalArgumentException.class,()-> anagramService.checkAnagram(texts, true));
    }
}
