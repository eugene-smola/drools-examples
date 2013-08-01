package com.smolnij.drools.utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Filter to filter out all excess files which are got into rules folder by some reason
 */
public class DrlFilenameFilter implements FilenameFilter {
	@Override
	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith(".drl");
	}
}