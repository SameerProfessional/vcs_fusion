package com.vcs_java_system.vcsfusion;

import com.vcs_java_system.vcsfusion.commands.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            System.exit(1);
        }

        String command = args[0];
        String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);

        try {
            switch (command.toLowerCase()) {
                case "init":
                    new InitCommand().execute(commandArgs);
                    break;
                case "help":
                case "--help":
                case "-h":
                    printUsage();
                    break;
                default:
                    System.err.println("Unknown command: " + command);
                    System.err.println("Currently only 'init' command is implemented.");
                    printUsage();
                    System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            if (System.getProperty("debug") != null) {
                e.printStackTrace();
            }
            System.exit(1);
        }
    }

    private static void printUsage() {
        System.out.println("VCS Fusion - A Modern Version Control System");
        System.out.println();
        System.out.println("Usage: vcs <command> [args]");
        System.out.println();
        System.out.println("Available commands:");
        System.out.println("  init      Initialize a new repository");
        System.out.println("  help      Show this help message");
        System.out.println();
        System.out.println("Coming soon: add, commit, status, log");
        System.out.println();
        System.out.println("Use 'vcs <command> --help' for more information on a command.");
    }
}
