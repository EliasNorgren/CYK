package Grammar;

import java.io.*;
import java.util.*;

public class Grammar {
    private final HashMap<Character, Character> terminalRules;
    private final HashMap<Character, ArrayList<char[]>> nonTerminalRules;
//    protected int nonTerminalRules
    private int [][] terminalRulesMapped;
    public int [][] nonTerminalRulesMapped;
    public int numberNonTerminalRules;
    protected int numberOfUniqueNonTerminals;

    private HashMap<Integer, Character> nonTerminalMappings = new HashMap<>();
    private HashMap<Character, Integer> integerNonTerminalMappings = new HashMap<>();
    public Grammar (File file) throws IOException {
        terminalRules = new HashMap<>();
        nonTerminalRules = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(file));
        Set<Character> characters = new HashSet<>();
        int rules = Integer.parseInt(br.readLine());
        numberNonTerminalRules = 0;
        for(int i = 0; i < rules; i++){
            String read = br.readLine();
            String[] splitted = read.split(" ");
            characters.add(splitted[0].charAt(0));

            if(splitted[1].length() == 1){
                terminalRules.put(splitted[0].charAt(0), splitted[1].charAt(0));
            }else{
                nonTerminalRules.computeIfAbsent(splitted[0].charAt(0), k -> new ArrayList<>());
                nonTerminalRules.get(splitted[0].charAt(0)).add(splitted[1].toCharArray());
                numberNonTerminalRules++;
            }
        }
        this.numberOfUniqueNonTerminals = characters.size();
        System.out.println(this.numberOfUniqueNonTerminals);

        nonTerminalRulesMapped = new int[numberNonTerminalRules][3];

        Iterator<Character> it = characters.iterator();
        int i = 0;
        while (it.hasNext()){
            char c = it.next();
            nonTerminalMappings.put(i, c);
            integerNonTerminalMappings.put(c, i);
            i++;
        }
        i = 0;
        for (Map.Entry<Character, ArrayList<char[]>> entry : nonTerminalRules.entrySet()) {
            for(char[] c : entry.getValue()){
                nonTerminalRulesMapped[i][0] = this.NonTerminalToInt(entry.getKey());
                nonTerminalRulesMapped[i][1] = this.NonTerminalToInt(c[0]);
                nonTerminalRulesMapped[i][2] = this.NonTerminalToInt(c[1]);
                i++;
            }
        }

    }
    public int getNumberOfUniqueNonTerminals() {
        return numberOfUniqueNonTerminals;
    }

    public ArrayList<char[]> getAllNonTerminalsFromRule(char rule) {
        return nonTerminalRules.get(rule);
    }

    public HashMap<Character, Character> getAllTerminals(){
        return new HashMap<>(terminalRules);
    }

    public HashMap<Character, ArrayList<char[]>> getAllNonTerminals(){
        return new HashMap<>(nonTerminalRules);
    }

    public boolean terminalRuleExists(char a, char b) {
        if(terminalRules.get(a) == null){
            return false;
        }
        return terminalRules.get(a) == b;
    }

    public int NonTerminalToInt(Character key) {
        return integerNonTerminalMappings.get(key);
    }
}
