package Parsers;

import Grammar.Grammar;
import org.junit.jupiter.api.Test;
import Enumerator.StringEnumerator;
import java.io.File;

class ParserTest {

    @org.junit.jupiter.api.Test
    void parseNaive() throws Exception {
        System.out.println("Naive");
        Grammar grammar = new Grammar(new File("Resource/dyckGrammar.txt"));
        Parser p = new Parser(grammar);
        StringBuilder sb = new StringBuilder("()");
        StringBuilder sb2 = new StringBuilder("()");

        for (int i = 0; i < 5; i++) {

            sb2.insert(sb.length(), ")", 0, 1);
            assert !p.parseNaive(sb2.toString());
            System.out.println("Doing \"" + sb2 + "\" - " + p.counter);

            assert p.parseNaive(sb.toString());
            sb.append('(');
            assert !p.parseNaive(sb.toString());;
            sb.append(')');

            sb2.insert(0, "(", 0, 1);
            assert p.parseNaive(sb2.toString());
            System.out.println("Doing \"" + sb2 + "\" - " + p.counter);

        }


        grammar = new Grammar(new File("Resource/otherGrammar.txt"));
        p = new Parser(grammar);

        assert p.parseNaive("baaba");
        System.out.println(p.counter);
        assert !p.parseNaive("baabaa");
    }

    @org.junit.jupiter.api.Test
    void parseTD() throws Exception {
        System.out.println("TD");

        Grammar grammar = new Grammar(new File("Resource/dyckGrammar.txt"));
        Parser p = new Parser(grammar);
        StringBuilder sb = new StringBuilder("()");
        StringBuilder sb2 = new StringBuilder("()");

        for (int i = 0; i < 8; i++) {

            sb2.insert(sb.length(), ")", 0, 1);
            boolean x = p.parseTD(sb2.toString());
            assert !x;
            System.out.println("Doing \"" + sb2 + "\" - " + p.counter);


            assert p.parseTD(sb.toString());
            sb.append('(');
            assert !p.parseTD(sb.toString());;
            sb.append(')');

            sb2.insert(0, "(", 0, 1);
            assert p.parseTD(sb2.toString());
            System.out.println("Doing \"" + sb2 + "\" - " + p.counter + " even ones");

        }


        grammar = new Grammar(new File("Resource/otherGrammar.txt"));
        p = new Parser(grammar);

        assert p.parseTD("baaba");
        assert !p.parseTD("baabaa");
    }

    @org.junit.jupiter.api.Test
    void parseBU() throws Exception {
        System.out.println("BU");

        Grammar grammar = new Grammar(new File("Resource/dyckGrammar.txt"));
        Parser p = new Parser(grammar);
        StringBuilder sb = new StringBuilder("()");
        StringBuilder sb2 = new StringBuilder("()");

        for (int i = 0; i < 5; i++) {

            sb2.insert(sb.length(), ")", 0, 1);
            assert !p.parseBU(sb2.toString());
            System.out.println("Doing \"" + sb2 + "\" - " + p.counter);

            assert p.parseBU(sb.toString());
            sb.append('(');
            assert !p.parseBU(sb.toString());;
            sb.append(')');

            sb2.insert(0, "(", 0, 1);
            assert p.parseBU(sb2.toString());
            System.out.println("Doing \"" + sb2 + "\" - " + p.counter);

        }


        grammar = new Grammar(new File("Resource/otherGrammar.txt"));
        p = new Parser(grammar);

        assert p.parseBU("baaba");
        assert !p.parseBU("baabaa");
    }

    @Test
    void linearGrammar() throws Exception {
        Grammar grammar = new Grammar(new File("Resource/linearGrammar.txt"));
        Grammar grammar2 = new Grammar(new File("Resource/linearCNFGrammar.txt"));

        Parser p = new Parser(grammar);
        Parser p2 = new Parser(grammar2);

        assert p.parseLinear("abbc");
        assert p2.parseTD("abbc");

        assert !p.parseLinear("abcc");
        assert !p2.parseTD("abcc");

        StringBuilder sb = new StringBuilder("abc");

        for(int i = 0; i < 15; i++){
            System.out.println(sb + " true");
            assert p.parseLinear(sb.toString());
            assert p2.parseTD(sb.toString());

            sb.append('c');
            System.out.println(sb + " false");
            assert !p.parseLinear(sb.toString());
            assert !p2.parseTD(sb.toString());

            sb.insert(0, 'a');
            System.out.println(sb + " true");
            assert p.parseLinear(sb.toString());
            assert p2.parseTD(sb.toString());


        }
        System.out.println();
        sb = new StringBuilder("abc");
        for(int i = 0; i < 20; i++){
            sb.insert(0, "ab");
            sb.append('c');
            System.out.println(sb + " true");
            assert p.parseLinear(sb.toString());
            assert p2.parseTD(sb.toString());
            System.out.print(" count "  + p.counter);
        }

        System.out.println();
        sb = new StringBuilder("aac");
        for(int i = 0; i < 15; i++){
            sb.append('c');
            System.out.println(sb + " false");
            assert !p.parseLinear(sb.toString());
            assert !p2.parseTD(sb.toString());
        }
    }

    @Test
    void test2() throws Exception {
        Grammar grammar = new Grammar(new File("Resource/aaaLinearGrammar.txt"));
//        Grammar grammar = new Grammar(new File("Resource/aaaLinearCNF.txt"));

        Parser p = new Parser(grammar);

        StringBuilder sb = new StringBuilder("aba");
        for(int i = 0; i < 1; i++){
//            String s = se.getNext();
            for(int j = 0; j < 1; j ++) {
                sb.append('a');
                sb.insert(0, "a");
            }

            boolean ret = p.parseLinear(sb.toString());
//            System.out.println("len: " + sb.length() +  " count: " + p.counter + " time: " + p.time + " value: " + ret);
            System.out.println(sb + " " + p.counter);

        }
    }
}