import Grammar.Grammar;
import Parsers.Parser;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        Grammar grammar = new Grammar(new File("Resource/dyckGrammar.txt"));
        Parser p = new Parser(grammar);
        StringBuilder sb = new StringBuilder("(");
//        System.out.println(p.parseTD("()()") + " " + p.counter);

        for(int i = 0; i < 8; i ++){

            System.out.print("Doing \"" + sb + "\" - " + i +  " ");
            System.out.print(p.parseTD(sb.toString()));
            System.out.println(" - " + p.counter + " recursions" + " - " + p.time + " ms");

            // 4 parenterser nästlade 900 diff naiva
            // 5 20 k

//            sb.insert(0, "(", 0, 1);
            sb.append(")");
//
            System.out.print("Doing \"" + sb + "\" - " + i + " ");
            System.out.print(p.parseNaive(sb.toString()));
            System.out.println(" - " + p.counter + " recursions");

//            sb.insert(sb.length(), ")", 0, 1);
            sb.append("(");

        }

    }
}
