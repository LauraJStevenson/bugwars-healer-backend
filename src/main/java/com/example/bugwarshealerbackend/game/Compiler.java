package com.example.bugwarshealerbackend.game;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Compiler {
    // Deliminator between the label and the command
    public static final String LABEL_DELIM = " ";
    public static final Map<String, Integer> COMMANDS = new HashMap<>();
    public static Pattern PATTERN;
    static {

        COMMANDS.put("noop", 0);
        COMMANDS.put("mov", 10);
        COMMANDS.put("rotr", 11);
        COMMANDS.put("rotl", 12);
        COMMANDS.put("att", 13);
        COMMANDS.put("eat", 14);
        COMMANDS.put("ifEnemy", 30);
        COMMANDS.put("ifAlly", 31);
        COMMANDS.put("ifFood", 32);
        COMMANDS.put("ifEmpty", 33);
        COMMANDS.put("ifWall", 34);
        COMMANDS.put("goto", 35);
        String REGEX = "^[a-zA-Z0-9_]*$";
        PATTERN = Pattern.compile(REGEX);

    }

    public static List<Integer> compile(String script) {

        List<Integer> commands = new ArrayList<>();
        Map<String, Integer> labelToIndexMap = new HashMap<>();
        List<String> labels = new ArrayList<>();

        //gets each line of the scripts
        String[] lines = script.split("\n");

        for(String line: lines) {

            //looking label
            if(line.startsWith(":")) {
                String[] parts = line.split(LABEL_DELIM);
                String label =  parts[0].substring(1);
                int labelIndex = commands.size();
                labelToIndexMap.put(label, labelIndex);

                //process command
                String command = parts[1];
                int compiledCommand = COMMANDS.get(command);
                commands.add(compiledCommand);
            } else {
                //control command
                if(line.contains(LABEL_DELIM)) {
                    String[] parts = line.split(LABEL_DELIM);
                    String command = parts[0];
                    String label = parts[1];
                    commands.add(COMMANDS.get(command));
                    commands.add(-1);
                    labels.add(label);
                } else {
                    commands.add(COMMANDS.get(line));
                }
            }
        }
        for(int i = 0; i < commands.size(); i++) {
            if(commands.get(i) == -1) {
                int labelIndex = labelToIndexMap.get(labels.get(0));
                commands.set(i,labelIndex);
                labels.remove(0);
            }
        } return commands;
    }

    public static boolean validate(String script) {

        String[] lines = script.split("\n");
        HashSet<String> labelLeft = new HashSet<>();
        List<String> labelRight = new ArrayList<>();

        for(String line: lines) {
            if(line.startsWith(":")) {
                String[] parts = line.split(LABEL_DELIM);
                if(parts.length != 2){
                    return false;
                }
                String label = parts[0].substring(1);
                if(!label.toUpperCase().equals(label)){
                    return false;
                }
                if(!Character.isLetter(label.charAt(0))) {
                    return false;
                }
                //Alphanumeric checking
                Matcher matcher = PATTERN.matcher(label);
                boolean result = matcher.matches();
                if(!result){
                    return false;
                }
                labelLeft.add(label);
                String command = parts[1];
                if(!COMMANDS.containsKey(command)){
                    return false;
                }
            } else {
                if(line.contains(LABEL_DELIM)){
                    String[] parts = line.split(LABEL_DELIM);
                    if(parts.length != 2) {
                        return false;
                    }
                    String command = parts[0];
                    if(!COMMANDS.containsKey(command)){
                        return false;
                    }
                    String label = parts[1];
                    labelRight.add(label);
                } else {
                    if(!COMMANDS.containsKey(line)){
                        return false;
                    }
                }
            }
        }
        for(String label: labelRight) {
            if(!labelLeft.contains(label)) {
                return false;
            }
        } return true;
    }
}
