import Grammar.Grammar;
import Parsers.Parser;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Grammar grammar = new Grammar(new File("Resource/dyckGrammar.txt"));
        Parser p = new Parser(grammar);
        StringBuilder sb = new StringBuilder("()");
//        System.out.println(p.parseBU("baaba"));

        for(int i = 0; i < 2; i ++){

            System.out.print("Doing \"" + sb + "\" - ");
            System.out.print(p.parseNaive(sb.toString()));
            System.out.println(" - " + p.counter + " recursions");

            sb.append('(');
//            sb.insert(0, "(", 0, 1);

            System.out.print("Doing \"" + sb + "\" - ");
            System.out.print(p.parseNaive(sb.toString()));
            System.out.println(" - " + p.counter + " recursions");

            sb.append(')');
//            sb.insert(sb.length(), ")", 0, 1);
        }

    }
}
