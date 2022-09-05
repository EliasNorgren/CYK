import Grammar.Grammar;
import Parsers.Parser;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Grammar grammar = new Grammar(new File("Resource/dyckGrammar.txt"));
        Parser p = new Parser(grammar);
        StringBuilder sb = new StringBuilder("()");
        for(int i = 0; i < 1; i ++){

            System.out.print("Doing \"" + sb + "\" - ");
            System.out.print(p.parseBU(sb.toString()));
            System.out.println(" - " + p.counter + " recursions");

            sb.insert(0, "(", 0, 1);

            System.out.print("Doing \"" + sb + "\" - ");
            System.out.print(p.parseBU(sb.toString()));
            System.out.println(" - " + p.counter + " recursions");

            sb.insert(sb.length(), ")", 0, 1);
        }

    }
}
