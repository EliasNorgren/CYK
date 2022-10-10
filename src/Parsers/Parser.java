package Parsers;

import Grammar.*;
public class Parser {

    private final Grammar grammar;
    public long counter = 0;
    public long time = 0;

    public Parser(Grammar grammar){
        this.grammar = grammar;
    }

//    Naive parser  --------------------------
    public boolean parseNaive(String input) throws CharacterNotFoundException {
        counter = 0;
        startTimer();
        boolean res =  naiveRec(0, grammar.convertStringToInts(input), 0 , input.length());
        stopTimer();
        return res;
    }

    private boolean naiveRec(int rule, int[] input, int i, int j){
        counter ++;
        if(i == j - 1){
            return grammar.terminalRuleExists(rule, input[i]);
        }

        for(int k = i + 1; k < j; k++){
            for(int r = 0; r < grammar.numberNonTerminalRules; r++){
                if(grammar.nonTerminalRules[r][0] != rule){
                    continue;
                }
                if(naiveRec(grammar.nonTerminalRules[r][1], input, i, k) &&
                         naiveRec(grammar.nonTerminalRules[r][2], input, k, j)){
                     return true;
                 }
            }
        }
        return false;
    }

    //    Bottom up parser  --------------------------
    public boolean parseBU(String input) throws CharacterNotFoundException {
        startTimer();
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

                        int ra = grammar.nonTerminalRules[rule][0];
                        int rb = grammar.nonTerminalRules[rule][1];
                        int rc = grammar.nonTerminalRules[rule][2];

                        if(table[split][c][rb] && table[len-split-1][c+split+1][rc]){
                            table[len][c][ra] = true;
                        }
                        counter ++;
                    }
                }
            }
        }
        stopTimer();
        return table[n-1][0][0];
    }

    //    Top down parser  --------------------------
    public boolean parseTD(String input) throws CharacterNotFoundException {
        counter = 0;
        Boolean [][][] table = new Boolean[input.length()][input.length()][grammar.numberOfUniqueNonTerminals];
        for(int i = 0; i < input.length(); i++){
            for(int j = 0; j < input.length(); j++){
                for(int k = 0; k < grammar.numberOfUniqueNonTerminals; k++){
                    table[i][j][k] = null;
                }
            }
        }
        startTimer();
        boolean res =  TDrec(0, grammar.convertStringToInts(input), 0 , input.length() - 1, table);
        stopTimer();
        return res;
    }

    private boolean TDrec(int rule, int[] input, int i, int j, Boolean [][][] table){
        counter ++;
        if(table[i][j][rule] != null){
            return table[i][j][rule];
        }
        if(i == j){
            table[i][j][rule] = grammar.terminalRuleExists(rule, input[i]);
            return table[i][j][rule];
        }
        for(int k = i; k < j; k++){
            for(int r = 0; r < grammar.numberNonTerminalRules; r++){
                if(grammar.nonTerminalRules[r][0] != rule){
                    continue;
                }
                table[i][k][grammar.nonTerminalRules[r][1]] = TDrec(grammar.nonTerminalRules[r][1], input, i, k, table);
                if(!table[i][k][grammar.nonTerminalRules[r][1]]){
                    continue;
                }
                table[k+1][j][grammar.nonTerminalRules[r][2]] = TDrec(grammar.nonTerminalRules[r][2], input, k+1, j, table);
                if(table[k+1][j][grammar.nonTerminalRules[r][2]]){
                    table[i][j][rule] = true;
                    return true;
                }
            }
        }
        table[i][j][rule] = false;
        return false;
    }

    public boolean parseLinear(String input) throws CharacterNotFoundException {
        counter = 0;
        Boolean [][][] table = new Boolean[input.length()][input.length()][grammar.numberOfUniqueNonTerminals];
        for(int i = 0; i < input.length(); i++){
            for(int j = 0; j < input.length(); j++){
                for(int k = 0; k < grammar.numberOfUniqueNonTerminals; k++){
                    table[i][j][k] = null;
                }
            }
        }
        startTimer();
        boolean res =  linRec(0, grammar.convertStringToInts(input), 0 , input.length()-1, table);
        stopTimer();
        return res;
    }

    private boolean linRec(int rule, int[] input, int i, int j, Boolean[][][] table){
        counter ++;
        if(i == j){
            return grammar.terminalRuleExists(rule, input[i]);
        }
        if(table[i][j][rule] != null){
            System.out.println("TAB");
            return table[i][j][rule];
        }

        for(int r = 0; r < grammar.numberNonTerminalRules; r ++){
            if(grammar.nonTerminalRules[r][0] != rule){
                continue;
            }
            // Left-linear
            if(grammar.isTerminal(grammar.nonTerminalRules[r][1]) && grammar.nonTerminalRules[r][2] == input[j]){
                if(linRec(grammar.nonTerminalRules[r][1], input, i, j-1, table)){
                    table[i][j][rule] = true;
                    return true;
                }
            // Right-linear
            }else if(grammar.isTerminal(grammar.nonTerminalRules[r][2]) && grammar.nonTerminalRules[r][1] == input[i]){
                if(linRec(grammar.nonTerminalRules[r][2], input, i+1, j, table)){
                    table[i][j][rule] = true;
                    return true;
                }
            }
        }
        table[i][j][rule] = false;
        return false;
    }

    private void startTimer(){
        time = System.currentTimeMillis();
    }

    private void stopTimer(){
        time = System.currentTimeMillis() - time;
    }

    public boolean parse(String parser, String input) throws CharacterNotFoundException{
        switch (parser) {
            case "BU" : return parseBU(input);
            case "TD" : return parseTD(input);
            case "naive" : return  parseNaive(input);
            case "linear" : return parseLinear(input);
            default : throw new CharacterNotFoundException(parser + " is not a valid parser");
        }
    }
}
