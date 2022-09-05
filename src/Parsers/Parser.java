package Parsers;

import Grammar.Grammar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Parser {

    private final Grammar grammar;
    public int counter = 0;

    public Parser(Grammar grammar){
        this.grammar = grammar;
    }

//    Naive parser  --------------------------
    public boolean parseNaive(String input){
        counter = 0;
        return naiveRec('S', input);
    }

    private boolean naiveRec(char rule, String input){
        counter ++;
        if(input.length() == 1){
            return grammar.terminalRuleExists(rule, input.charAt(0));
        }
        ArrayList<char[]> rules = grammar.getAllNonTerminalsFromRule(rule);
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

    //    Bottom up parser  --------------------------
    public boolean parseBU(String input){
        int n = input.length();
        boolean[][][] table = new boolean[n][n][grammar.getNumberOfUniqueNonTerminals()];

        for(int i = 0; i < n; i++){
            // Find which rule that produces input[i]
            for (Map.Entry<Character, Character> entry : grammar.getAllTerminals().entrySet()) {
                if (entry.getValue() == input.charAt(i)) {
                    table[0][i][grammar.NonTerminalToInt(entry.getKey())] = true;
                    break;
                }
            }
        }

        for(int len = 1; len < n; len++){
            for(int c = 0; c < n - len + 1; c++){
                for(int split = 0; split < len; split++){
                    for (int rule = 0; rule < grammar.numberNonTerminalRules; rule ++) {
                        int ra = grammar.nonTerminalRulesMapped[rule][0];
                        int rb = grammar.nonTerminalRulesMapped[rule][1];
                        int rc = grammar.nonTerminalRulesMapped[rule][2];

                        if(table[split][c][rb] && table[len-split][c+split][rc]){
                            table[len][c][ra] = true;
                        }
                    }
                }
            }
        }

        return table[n-1][0][0];
    }
}
