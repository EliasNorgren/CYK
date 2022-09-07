package Grammar;

import java.io.*;
import java.util.*;

public class Grammar {
    private final HashMap<Character, Character> terminalRules;
    private final HashMap<Character, ArrayList<char[]>> nonTerminalRules;
//    protected int nonTerminalRules
    public int [][] terminalRulesMapped;
    public int [][] nonTerminalRulesMapped;
    public int numberNonTerminalRules;
    public int numberTerminalRules;
    int numberOfUniqueNonTerminals;

    private HashMap<Integer, Character> nonTerminalMappings = new HashMap<>();
    private final HashMap<Character, Integer> integerNonTerminalMappings = new HashMap<>();

    private HashMap<Integer, Character> terminalMappings = new HashMap<>();
    private final HashMap<Character, Integer> integerTerminalMappings = new HashMap<>();

    public Grammar (File file) throws IOException {
        terminalRules = new HashMap<>();
        nonTerminalRules = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(file));
        Set<Character> characters = new HashSet<>();
        Set<Character> terminalCharacters = new HashSet<>();

        int rules = Integer.parseInt(br.readLine());
        numberNonTerminalRules = 0;
        for(int i = 0; i < rules; i++){
            String read = br.readLine();
            String[] splitted = read.split(" ");
            characters.add(splitted[0].charAt(0));

            if(splitted[1].length() == 1){
                terminalRules.put(splitted[0].charAt(0), splitted[1].charAt(0));
                terminalCharacters.add(splitted[1].charAt(0));
                numberTerminalRules++;
            }else{
                nonTerminalRules.computeIfAbsent(splitted[0].charAt(0), k -> new ArrayList<>());
                nonTerminalRules.get(splitted[0].charAt(0)).add(splitted[1].toCharArray());
                numberNonTerminalRules++;
                characters.add(splitted[1].charAt(0));
                characters.add(splitted[1].charAt(1));
            }
        }

        this.numberOfUniqueNonTerminals = characters.size();
        nonTerminalRulesMapped = new int[numberNonTerminalRules][3];

        Iterator<Character> it = characters.iterator();
        int i = 0;
        while (it.hasNext()){
            char c = it.next();
            nonTerminalMappings.put(i, c);
            integerNonTerminalMappings.put(c, i);
            i++;
        }

        Iterator<Character> iter = terminalCharacters.iterator();
//        i = 0;
        while (iter.hasNext()){
            char c = iter.next();
            terminalMappings.put(i, c);
            integerTerminalMappings.put(c, i);
            i++;
        }

        i = 0;
        for (Map.Entry<Character, ArrayList<char[]>> entry : nonTerminalRules.entrySet()) {
            for(char[] c : entry.getValue()){
                nonTerminalRulesMapped[i][0] = this.nonTerminalToInt(entry.getKey());
                nonTerminalRulesMapped[i][1] = this.nonTerminalToInt(c[0]);
                nonTerminalRulesMapped[i][2] = this.nonTerminalToInt(c[1]);
                i++;
            }
        }

        terminalRulesMapped = new int[numberTerminalRules][2];
        System.out.println(numberTerminalRules + " - " + terminalRules.size());
        i = 0;
        for(Map.Entry<Character, Character> entry : terminalRules.entrySet()){
            terminalRulesMapped[i][0] = this.nonTerminalToInt(entry.getKey());
            terminalRulesMapped[i][1] = this.terminalToInt(entry.getValue());
            i++;
        }
    }

    public int terminalToInt(Character value) {
        return this.integerTerminalMappings.get(value);
    }

    public int getNumberOfUniqueNonTerminals() {
        return numberOfUniqueNonTerminals;
    }

    public ArrayList<char[]> getAllNonTerminalsFromRule(char rule) {
        return nonTerminalRules.get(rule);
    }

    public boolean terminalRuleExists(char a, char b) {
        if(terminalRules.get(a) == null){
            return false;
        }
        return terminalRules.get(a) == b;
    }

    public int nonTerminalToInt(Character key) {
        return integerNonTerminalMappings.get(key);
    }
}
