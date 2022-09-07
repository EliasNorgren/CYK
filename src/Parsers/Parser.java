package Parsers;

import Grammar.Grammar;

public class Parser {

    private final Grammar grammar;
    public int counter = 0;

    public Parser(Grammar grammar){
        this.grammar = grammar;
    }

//    Naive parser  --------------------------
    public boolean parseNaive(String input) throws Exception {
        counter = 0;
        return naiveRec(grammar.characterToInt('S'), grammar.convertStringToInts(input), 0 , input.length()-1);
    }

    private boolean naiveRec(int rule, int[] input, int i, int j){
        counter ++;
        if(i == j){
            return grammar.terminalRuleExists(rule, input[i]);
        }

        for(int r = 0; r < grammar.numberNonTerminalRules; r++){
            if(grammar.nonTerminalRulesMapped[r][0] != rule){
                continue;
            }
            int k = i;
            while(k < j){
                boolean b1 = naiveRec(grammar.nonTerminalRulesMapped[r][1], input, i, k);
                boolean b2 = naiveRec(grammar.nonTerminalRulesMapped[r][2], input, k+1, j);
                if(b1 && b2){
                    return true;
                }
                k++;
            }
        }
        return false;
    }

    //    Bottom up parser  --------------------------
    public boolean parseBU(String input) throws Exception {
        int[] cInput = grammar.convertStringToInts(input);
        counter = 0;
        int n = cInput.length;
        boolean[][][] table = new boolean[n][n][grammar.numberOfUniqueNonTerminals];

        for(int i = 0; i < n; i++){
            // Find which rules that produces input[i]
            for(int rule = 0; rule < grammar.numberTerminalRules; rule++){
                if(cInput[i] == grammar.terminalRulesMapped[rule][1]){
                    table[0][i][grammar.terminalRulesMapped[rule][0]] = true;
                }
            }
        }

        for(int len = 1; len < n; len++){
            for(int c = 0; c < n - len; c++){
                for(int split = 0; split < len; split++){
                    for (int rule = 0; rule < grammar.numberNonTerminalRules; rule ++) {

                        int ra = grammar.nonTerminalRulesMapped[rule][0];
                        int rb = grammar.nonTerminalRulesMapped[rule][1];
                        int rc = grammar.nonTerminalRulesMapped[rule][2];

                        if(table[split][c][rb] && table[len-split-1][c+split+1][rc]){
                            table[len][c][ra] = true;
                        }
                        counter ++;
                    }
                }
            }
        }
        return table[n-1][0][grammar.characterToInt('S')];
    }
}
