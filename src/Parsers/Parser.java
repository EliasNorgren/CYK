package Parsers;

import Grammar.Grammar;

import java.util.ArrayList;

public class Parser {

    private final Grammar grammar;
    int counter = 0;

    public Parser(Grammar grammar){
        this.grammar = grammar;
    }

    public boolean parseNaive(String input){
        counter = 0;
        return naiveRec('S', input);
    }

    private boolean naiveRec(char rule, String input){
        counter ++;
        if(input.length() == 1){
            return grammar.terminalRuleExists(rule, input.charAt(0));
        }
        ArrayList<char[]> rules = grammar.getAllNonTerminalRules(rule);
        if(rules == null){
            return false;
        }
        for(char[] r : rules){
            for(int k = 1; k < input.length(); k++){
                boolean b1 = naiveRec(r[0], input.substring(0,k));
                boolean b2 = naiveRec(r[1], input.substring(k));
                if(b1 && b2){
                    return true;
                }
            }
        }
        return false;
    }
}
