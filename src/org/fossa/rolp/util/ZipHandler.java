/**
 * Copyright (c) 2013, 2014 Frank Kaddereit, Anne Lachnitt, http://www.fossa.de/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.fossa.rolp.util;

import java.io.File;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipHandler {
	private static final int COMPRESSION_METHOD = Zip4jConstants.COMP_DEFLATE;
	private static final int COMPRESSION_LEVEL = Zip4jConstants.DEFLATE_LEVEL_ULTRA;
	
	private ZipFile zipFile;
	private ArrayList<File> filesToAdd;
	
	public ZipHandler(String zipFilepath) throws ZipException {
		zipFile = new ZipFile(zipFilepath);
		filesToAdd = new ArrayList<File>();
	}
	
	public void addFile(String filepath) {
		filesToAdd.add(new File(filepath));
	}
	
	public void finalizeZipFile() throws ZipException {
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(COMPRESSION_METHOD);
		parameters.setCompressionLevel(COMPRESSION_LEVEL);		
		zipFile.addFiles(filesToAdd, parameters);
	}
	
	public String getFilePath() {
		return zipFile.getFile().getAbsolutePath();
	}
	
	public ArrayList<File> getFiles() {
		return filesToAdd;
	}

}
