package Grammar;

import java.io.*;
import java.util.*;

public class Grammar {

    public int [][] terminalRulesMapped;
    public int [][] nonTerminalRules;
    public int numberNonTerminalRules;
    public int numberTerminalRules;
    public int numberOfUniqueNonTerminals;
    private final int [] characterMappingTable;

    public Grammar (File file) throws FileNotFoundException, CharacterNotFoundException {
        HashMap<Character, Character> terminalRules = new HashMap<>();
        HashMap<Character, ArrayList<char[]>> nonTerminalRules = new HashMap<>();

        Scanner sc = new Scanner(new FileReader(file));
        Set<Character> characters = new HashSet<>();
        Set<Character> terminalCharacters = new HashSet<>();
        boolean firstRead = false;
        int start = -1;
        numberNonTerminalRules = 0;
        while(sc.hasNext()){
            String read = sc.nextLine();
            String[] splitted = read.split(" ");
            characters.add(splitted[0].charAt(0));

            if(!firstRead){
                firstRead = true;
                start = splitted[0].charAt(0);
            }

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
        this.nonTerminalRules = new int[numberNonTerminalRules][3];

        characterMappingTable = new int[characters.size() + terminalCharacters.size()];
        characterMappingTable[0] = start;
        characters.remove((char) start);
        int i = 1;
        for(char c : characters){
            characterMappingTable[i] = c;
            i++;
        }
        for (char c : terminalCharacters) {
            characterMappingTable[i] = c;
            i++;
        }
        i = 0;
        for (Map.Entry<Character, ArrayList<char[]>> entry : nonTerminalRules.entrySet()) {
            for(char[] c : entry.getValue()){
                this.nonTerminalRules[i][0] = this.characterToInt(entry.getKey());
                this.nonTerminalRules[i][1] = this.characterToInt(c[0]);
                this.nonTerminalRules[i][2] = this.characterToInt(c[1]);
                i++;
            }
        }
        terminalRulesMapped = new int[numberTerminalRules][2];
        i = 0;
        for(Map.Entry<Character, Character> entry : terminalRules.entrySet()){
            terminalRulesMapped[i][0] = this.characterToInt(entry.getKey());
            terminalRulesMapped[i][1] = this.characterToInt(entry.getValue());
            i++;
        }
    }

    public boolean terminalRuleExists(int a, int b) {
        for(int i = 0; i < numberTerminalRules; i++){
            if(terminalRulesMapped[i][0] == a && terminalRulesMapped[i][1] == b){
                return true;
            }
        }
        return false;
    }

    private int characterToInt(char key) throws CharacterNotFoundException {
        for(int j = 0; j < characterMappingTable.length; j++){
            if(key == characterMappingTable[j]){
                return j;
            }
        }
        throw new CharacterNotFoundException ("Character not found: " + key);
    }

    public int[] convertStringToInts(String input) throws CharacterNotFoundException {
        int [] result = new int[input.length()];
        for(int i = 0; i < input.length(); i++){
            result[i] = characterToInt(input.charAt(i));
        }
        return result;
    }
}
