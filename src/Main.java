import Grammar.*;
import Parsers.Parser;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws CharacterNotFoundException, FileNotFoundException {
        Grammar grammar = new Grammar(new File("Resource/dyckGrammar.txt"));
        Parser p = new Parser(grammar);

//        System.out.println(p.parseTD("(()))"));
//        StringEnumerator se = new StringEnumerator("()", "a", 100);
        StringBuilder sb = new StringBuilder("()");

        for(int i = 0; i < 50; i ++){
//            String s = se.getNext();
            System.out.print("Doing \"" + sb + "\" - " + (i+1) +  " ");
            System.gc();
            System.out.print(p.parseTD(sb.toString()));
            System.out.println(" - " + p.counter + " recursions" + " - " + p.time + " ms");

            sb.insert(0, "(", 0, 1);
//            sb.append("()");
//
//            System.out.print("Doing \"" + sb.length() + "\" - " + i + " ");
//            System.out.print(p.parseBU(sb.toString()));
//            System.out.println(" - " + p.counter + " recursions" + " - " + p.time + " ms");
//
            sb.insert(sb.length(), ")", 0, 1);
//            sb.append("()");

        }

    }
}
