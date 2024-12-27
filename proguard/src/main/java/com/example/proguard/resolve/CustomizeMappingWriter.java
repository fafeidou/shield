package com.example.proguard.resolve;

import java.io.*;

public class CustomizeMappingWriter implements Closeable {
    private final FileWriter fileWriter;

    private final BufferedWriter bufferedWriter;

    public CustomizeMappingWriter(File outputFile) throws IOException {
        this.fileWriter = new FileWriter(outputFile);
        this.bufferedWriter = new BufferedWriter(fileWriter);
    }

    public void writeLine(String content) throws IOException {
        this.bufferedWriter.write(content);
        this.bufferedWriter.newLine();
    }

    @Override
    public void close() throws IOException {
        this.bufferedWriter.flush();
        this.fileWriter.flush();

        this.bufferedWriter.close();
        this.fileWriter.close();
    }
}
