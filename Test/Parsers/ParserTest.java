package Parsers;

import Grammar.Grammar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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

        for (int i = 0; i < 5; i++) {

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
            System.out.println("Doing \"" + sb2 + "\" - " + p.counter);

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
}