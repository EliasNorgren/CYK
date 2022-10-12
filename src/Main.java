import Enumerator.StringEnumerator;
import Grammar.*;
import Parsers.Parser;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws CharacterNotFoundException, FileNotFoundException {
        if(!args[0].equals("tests")){
            String parser;
            switch (args[1]) {
                case "BU" : parser = "BU"; break;
                case "TD" : parser = "TD"; break;
                case "naive" : parser = "naive"; break;
                case "linear" : parser = "linear"; break;
                default :
                    System.out.println("Usage: \"java -jar CYK.jar [tests | file] [BU | TD | naive | linear] \"testString\"\"");
                    return;
            }
            Grammar grammar = new Grammar(new File(args[0]));
            Parser p = new Parser(grammar);
            boolean ret = p.parse(parser, args[2]);
            System.out.println("String : " + args[2] + " Counter: " + p.counter + " Time: " + p.time + " ms" + " Truth value: " + ret);
        }else{

            Grammar grammar = new Grammar(new File("dyckGrammar.txt"));
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

            grammar = new Grammar(new File("stupidGrammar.txt"));
            p = new Parser(grammar);

            se = new StringEnumerator("", "a", StringEnumerator.Index.END, 100);
            System.out.println("Doing a..a top-down");
            runTests(p, se, "TD");

            se = new StringEnumerator("", "a", StringEnumerator.Index.END, 100);
            System.out.println("Doing a..a bottom-up");
            runTests(p, se, "BU");

            System.out.println("Doing a..aba..a top-down linear CNF");
            grammar = new Grammar(new File("aaaLinearCNF.txt"));
            p = new Parser(grammar);
            StringBuilder sb = new StringBuilder("aba");
            for(int i = 0; i < 10; i++){

                boolean val = p.parseTD(sb.toString());
                System.out.println("Length: " + sb.length() + " Counter: " + p.counter + "  Time: " + p.time + " Truth value: " + val);
                sb.insert(0, 'a');
                sb.append('a');
            }

            System.out.println("Doing a..aba..a linear implementation");
            grammar = new Grammar(new File("aaaLinearGrammar.txt"));
            p = new Parser(grammar);
            sb = new StringBuilder("aba");
            for(int i = 0; i < 10; i++){

                boolean val = p.parseLinear(sb.toString());
                System.out.println("Length: " + sb.length() + " Counter: " + p.counter + "  Time: " + p.time + " Truth value: " + val);
                sb.insert(0, 'a');
                sb.append('a');
            }
        }
    }

    private static void runTests(Parser p, StringEnumerator se, String bu) throws CharacterNotFoundException {
        for (int i = 0; i < 5; i++) {
            String s = se.getNext();
            boolean val = p.parse(bu, s);
            System.out.println("Length: " + s.length() + " Counter: " + p.counter + "  Time: " + p.time + " Truth value: " + val);
        }
    }
}
