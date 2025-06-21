package com.vcs_java_system.vcsfusion.commands;

import com.vcs_java_system.vcsfusion.core.Repository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InitCommand implements Command {
    @Override
    public void execute(String[] args) throws Exception {
        Path currentDir = Paths.get(System.getProperty("user.dir"));
        Path vcsDir = currentDir.resolve(".vcsfusion");
        
        if (Files.exists(vcsDir)) {
            throw new RuntimeException("Repository already exists in " + currentDir);
        }
        
        // Create the VCS directory structure
        Files.createDirectories(vcsDir);
        Files.createDirectories(vcsDir.resolve("objects"));
        Files.createDirectories(vcsDir.resolve("refs").resolve("heads"));
        Files.createDirectories(vcsDir.resolve("refs").resolve("tags"));
        
        // Create initial files
        Files.write(vcsDir.resolve("HEAD"), "ref: refs/heads/main\n".getBytes());
        Files.write(vcsDir.resolve("config"), createInitialConfig().getBytes());
        Files.createFile(vcsDir.resolve("index"));
        
        System.out.println("Initialized empty VCS Fusion repository in " + vcsDir.toAbsolutePath());
        
        // Create a Repository instance to validate our setup
        new Repository(currentDir);
    }
    
    private String createInitialConfig() {
        return "[core]\n" +
               "    repositoryformatversion = 0\n" +
               "    filemode = true\n" +
               "    bare = false\n" +
               "[user]\n" +
               "    name = Your Name\n" +
               "    email = your.email@example.com\n";
    }
}
