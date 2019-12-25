package org.jemiahlabs.skrls.kdm.models.util;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ListFilesUtil {
	private Consumer<File> listener;
	
	public ListFilesUtil(Consumer<File> listener) {
		this.listener = listener;
	}
	
	public void listFiles(String fileName) {
		listFiles(fileName, file -> true);
    }
	
	public void listFiles(String fileName, Predicate<File> validFile) {
		File file = new File(fileName);
        if ( file.isFile() ) acceptFile(file, validFile);
        else if ( file.isDirectory() ) acceptDirectory(file, validFile);
    }
	
	private void acceptDirectory(File file, Predicate<File> validFile) {
		File[] files = file.listFiles();
        for (File fileCurrent : files) {
            if ( fileCurrent.isFile() ) acceptFile(fileCurrent, validFile);
            else if ( fileCurrent.isDirectory() ) acceptDirectory(fileCurrent, validFile);
        }
	}
	
	private void acceptFile(File file, Predicate<File> validFile) {
		if ( validFile.test(file) ) listener.accept(file);
	}
}
