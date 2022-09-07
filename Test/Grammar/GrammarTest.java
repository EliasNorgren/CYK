package Grammar;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GrammarTest {

    @Test
    public void test() throws IOException, CharacterNotFoundException {
        Grammar g = new Grammar(new File("Resource/dyckGrammar.txt"));
    }
}