package com.example.bugwarshealerbackend.game;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compiler {
    // Delimiter between the label and the command
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

    public static List<String> cleanScript(String script) {
        List<String> result = new ArrayList<>();
        String[] lines = script.split("\n");
        for (String line : lines) {
            String finalLine = line;
            int hashTagPosition = finalLine.indexOf("#");
            if (hashTagPosition != -1) {
                finalLine = finalLine.substring(0, hashTagPosition);
            }
            finalLine = finalLine.trim();
            finalLine = finalLine.replaceAll("\\s+", " ");
            if (!finalLine.isEmpty()) {
                result.add(finalLine);
            }
        }
        return result;
    }

    public static List<Integer> compile(String script) {
        List<Integer> commands = new ArrayList<>();
        Map<String, Integer> labelToIndexMap = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<String> lines = cleanScript(script);

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith(":")) {
                String[] parts = line.split("\\s+", 2);
                String label = parts[0].substring(1).trim();
                labelToIndexMap.put(label, commands.size());
                if (parts.length > 1) {
                    processCommand(parts[1], commands, labelToIndexMap, labels);
                }
            } else {
                processCommand(line, commands, labelToIndexMap, labels);
            }
        }
        resolveLabels(commands, labels, labelToIndexMap);
        return commands;
    }

    private static void processCommand(String line, List<Integer> commands, Map<String, Integer> labelToIndexMap, List<String> labels) {
        String[] parts = line.trim().split("\\s+");
        String command = parts[0];
        if (COMMANDS.containsKey(command)) {
            commands.add(COMMANDS.get(command));
            // Check if this command should be followed by a label
            if (parts.length > 1 && command.equals("goto")) { // Assuming only "goto" requires a label
                labels.add(parts[1]);
                commands.add(-1); // Placeholder for label index
            }
        } else {
            throw new IllegalArgumentException("Unknown command or incorrect script format: " + command);
        }
    }


    private static void resolveLabels(List<Integer> commands, List<String> labels, Map<String, Integer> labelToIndexMap) {
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i) == -1) {
                String label = labels.remove(0);
                Integer labelIndex = labelToIndexMap.get(label);
                if (labelIndex == null) {
                    throw new IllegalArgumentException("Undefined label: " + label);
                }
                commands.set(i, labelIndex);
            }
        }
    }

    public static boolean validate(String script) {
        List<String> lines = cleanScript(script);
        HashSet<String> labelLeft = new HashSet<>();
        List<String> labelRight = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith(":")) {
                String[] parts = line.split(LABEL_DELIM);
                if (parts.length > 2) {
                    return false;
                }
                String label = parts[0].substring(1);
                if (!label.toUpperCase().equals(label)) {
                    return false;
                }
                if (!Character.isLetter(label.charAt(0))) {
                    return false;
                }
                Matcher matcher = PATTERN.matcher(label);
                if (!matcher.matches()) {
                    return false;
                }
                labelLeft.add(label);
                if (parts.length == 2) {
                    String command = parts[1];
                    if (!COMMANDS.containsKey(command)) {
                        return false;
                    }
                }
            } else {
                if (line.contains(LABEL_DELIM)) {
                    String[] parts = line.split(LABEL_DELIM);
                    if (parts.length != 2) {
                        return false;
                    }
                    String command = parts[0];
                    if (!COMMANDS.containsKey(command)) {
                        return false;
                    }
                    String label = parts[1];
                    labelRight.add(label);
                } else {
                    if (!COMMANDS.containsKey(line)) {
                        return false;
                    }
                }
            }
        }
        for (String label : labelRight) {
            if (!labelLeft.contains(label)) {
                return false;
            }
        }
        return true;
    }
}
