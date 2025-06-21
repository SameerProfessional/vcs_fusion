package com.vcs_java_system.vcsfusion.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Repository {
    private final Path workingDirectory;
    private final Path vcsDirectory;
    
    public Repository(Path workingDirectory) throws IOException {
        this.workingDirectory = workingDirectory.toAbsolutePath();
        this.vcsDirectory = findVcsDirectory(this.workingDirectory);
        
        if (vcsDirectory == null) {
            throw new IOException("Not a VCS Fusion repository (or any of the parent directories)");
        }
    }
    
    private Path findVcsDirectory(Path startPath) {
        Path current = startPath;
        while (current != null) {
            Path vcsDir = current.resolve(".vcsfusion");
            if (Files.exists(vcsDir) && Files.isDirectory(vcsDir)) {
                return vcsDir;
            }
            current = current.getParent();
        }
        return null;
    }
    
    public Path getWorkingDirectory() {
        return workingDirectory;
    }
    
    public Path getVcsDirectory() {
        return vcsDirectory;
    }
    
    public Path getObjectsDirectory() {
        return vcsDirectory.resolve("objects");
    }
    
    public Path getRefsDirectory() {
        return vcsDirectory.resolve("refs");
    }
    
    public Path getIndexFile() {
        return vcsDirectory.resolve("index");
    }
    
    public Path getHeadFile() {
        return vcsDirectory.resolve("HEAD");
    }
    
    public String getCurrentBranch() throws IOException {
        String headContent = Files.readString(getHeadFile()).trim();
        if (headContent.startsWith("ref: ")) {
            String ref = headContent.substring(5);
            return ref.substring(ref.lastIndexOf('/') + 1);
        }
        return "detached HEAD";
    }
    
    public boolean isValidRepository() {
        return Files.exists(vcsDirectory) && 
               Files.exists(getObjectsDirectory()) && 
               Files.exists(getRefsDirectory()) && 
               Files.exists(getIndexFile()) && 
               Files.exists(getHeadFile());
    }
}
