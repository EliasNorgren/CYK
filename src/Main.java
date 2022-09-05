import Grammar.CYKGrammar;
import Parsers.Parser;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CYKGrammar grammar = new CYKGrammar(new File("Resource/dyckGrammar.txt"));
        Parser p = new Parser(grammar);
        String initial = "()";
        System.out.println(p.parseNaive("(()(()))"));
    }
}
