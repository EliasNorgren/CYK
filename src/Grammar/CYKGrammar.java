package Grammar;

import java.io.*;
import java.util.ArrayList;

public class CYKGrammar extends Grammar{

    public CYKGrammar(File file) throws IOException {
        super();
        BufferedReader br = new BufferedReader(new FileReader(file));
        int rules = Integer.parseInt(br.readLine());
        for(int i = 0; i < rules; i++){
            String read = br.readLine();
            String[] splitted = read.split(" ");
            if(splitted[1].length() == 1){
                terminalRules.put(splitted[0].charAt(0), splitted[1].charAt(0));
            }else{
                nonTerminalRules.computeIfAbsent(splitted[0].charAt(0), k -> new ArrayList<>());
                nonTerminalRules.get(splitted[0].charAt(0)).add(splitted[1].toCharArray());
            }
        }
    }


}
