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
        StringBuilder sb = new StringBuilder("((((()))))");
        System.out.println("String length - counter - time (ms)");
        for(int i = 0; i < 10; i ++){
//            String s = se.getNext();
            System.out.print(sb.length() + " ");
            System.gc();
            System.out.print(p.parseTD(sb.toString()));
            System.out.println(" " + p.counter + " " + p.time);


            for(int j = 0; j < 100; j++){
                sb.insert(0, "(", 0, 1);
                sb.insert(sb.length(), ")", 0, 1);
            }


        }

    }
}
