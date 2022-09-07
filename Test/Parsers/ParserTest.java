package Parsers;

import Grammar.Grammar;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @org.junit.jupiter.api.Test
    void parseNaive() throws Exception {
        Grammar grammar = new Grammar(new File("Resource/dyckGrammar.txt"));
        Parser p = new Parser(grammar);
        StringBuilder sb = new StringBuilder("()");
        StringBuilder sb2 = new StringBuilder("()");

        for (int i = 0; i < 5; i++) {

            sb2.insert(sb.length(), ")", 0, 1);
            System.out.println("Doing \"" + sb2 + "\" - ");
            assert !p.parseNaive(sb2.toString());

            assert p.parseNaive(sb.toString());
            sb.append('(');
            assert !p.parseNaive(sb.toString());;
            sb.append(')');

            sb2.insert(0, "(", 0, 1);
            System.out.println("Doing \"" + sb2 + "\" - ");
            assert p.parseNaive(sb2.toString());

        }


        grammar = new Grammar(new File("Resource/otherGrammar.txt"));
        p = new Parser(grammar);

        assert p.parseNaive("baaba");
        assert !p.parseNaive("baabaa");
    }

    @org.junit.jupiter.api.Test
    void parseBU() throws Exception {

        Grammar grammar = new Grammar(new File("Resource/dyckGrammar.txt"));
        Parser p = new Parser(grammar);
        StringBuilder sb = new StringBuilder("()");
        StringBuilder sb2 = new StringBuilder("()");

        for (int i = 0; i < 5; i++) {

            sb2.insert(sb.length(), ")", 0, 1);
            System.out.println("Doing \"" + sb2 + "\" - ");
            assert !p.parseBU(sb2.toString());

            assert p.parseBU(sb.toString());
            sb.append('(');
            assert !p.parseBU(sb.toString());;
            sb.append(')');

            sb2.insert(0, "(", 0, 1);
            System.out.println("Doing \"" + sb2 + "\" - ");
            assert p.parseBU(sb2.toString());

        }


        grammar = new Grammar(new File("Resource/otherGrammar.txt"));
        p = new Parser(grammar);

        assert p.parseBU("baaba");
        assert !p.parseBU("baabaa");
    }
}