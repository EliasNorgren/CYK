package Grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Grammar {
    protected HashMap<Character, Character> terminalRules;
    protected HashMap<Character, ArrayList<char[]>> nonTerminalRules;

    public Grammar (){
        terminalRules = new HashMap<>();
        nonTerminalRules = new HashMap<>();
    }

    public ArrayList<char[]> getAllNonTerminalRules(char rule) {
        return nonTerminalRules.get(rule);
    }

    public boolean terminalRuleExists(char a, char b) {
        if(terminalRules.get(a) == null){
            return false;
        }
        return terminalRules.get(a) == b;
    }
}
