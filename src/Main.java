import Grammar.*;
import Parsers.Parser;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws CharacterNotFoundException, FileNotFoundException {
        Grammar grammar = new Grammar(new File("Resource/dyckGrammar.txt"));
        Parser p = new Parser(grammar);

        StringEnumerator se = new StringEnumerator("()", "()", StringEnumerator.Index.MIDDLE, 100);
        System.out.println("Doing ((())) bottom up");
        runTests(p, se, "BU");

        se = new StringEnumerator("", "()", StringEnumerator.Index.END, 100);
        System.out.println("Doing ()()().. bottom up");
        runTests(p, se, "BU");


        se = new StringEnumerator(")", "()", StringEnumerator.Index.BEGINNING, 100);
        System.out.println("Doing ()..()) bottom up");
        runTests(p, se, "BU");

        se = new StringEnumerator(")", "()", StringEnumerator.Index.END, 100);
        System.out.println("Doing )()..() bottom up");
        runTests(p, se, "BU");

        se = new StringEnumerator("()", "()", StringEnumerator.Index.MIDDLE, 100);
        System.out.println("Doing ((())) top-down");
        runTests(p, se, "TD");

        se = new StringEnumerator("(", "()", StringEnumerator.Index.BEGINNING, 100);
        System.out.println("Doing ()..()( top-down");
        runTests(p, se, "TD");

        se = new StringEnumerator("", "()", StringEnumerator.Index.END, 100);
        System.out.println("Doing ()..() top-down");
        runTests(p, se, "TD");
        se = new StringEnumerator(")", "()", StringEnumerator.Index.END, 100);
        System.out.println("Doing )()..() top-down");
        runTests(p, se, "TD");

        grammar = new Grammar(new File("Resource/stupidGrammar.txt"));
        p = new Parser(grammar);

        se = new StringEnumerator("", "a", StringEnumerator.Index.END, 100);
        System.out.println("Doing a..a top-down");
        runTests(p, se, "TD");

        se = new StringEnumerator("", "a", StringEnumerator.Index.END, 100);
        System.out.println("Doing a..a bottom-up");
        runTests(p, se, "BU");

    }

    private static void runTests(Parser p, StringEnumerator se, String bu) throws CharacterNotFoundException {
        for (int i = 0; i < 5; i++) {
            String s = se.getNext();
            boolean val = p.parse(bu, s);
            System.out.println("Length: " + s.length() + " Counter: " + p.counter + "  Time: " + p.time + " Truth value: " + val);
        }
    }
}
