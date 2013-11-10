package com.smolinj.drools;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Filter to filter out all excess files which are got into rules folder by some reason
 */
public class FilenameExtensionFilter implements FilenameFilter {
	
	private String extension;
	
	public FilenameExtensionFilter(String extension) {
		if (extension == null) throw new NullPointerException("extention cannot be null");
		if (!extension.startsWith(".")) {
			extension = ".".concat(extension);
		}
		this.extension = extension.toLowerCase();
	}
	@Override
	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith(extension);
	}
}