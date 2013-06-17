package uk.co.samatkins;

import java.io.IOException;
import java.io.InputStream;

/**
 * Random Number Generator that actually just uses data from a file.
 * @author Sam
 *
 */
public class FileRNG {
	
	private InputStream file;

	public FileRNG(InputStream fileStream) {
		this.file = fileStream;
	}
	
	/**
	 * Reads 2 bytes from file, and returns an integer between min and max, inclusive
	 * @param min
	 * @param max
	 * @return
	 */
	public int getIntBetween(int min, int max) {
		try {
			int input = (this.file.read() << 8) + this.file.read();
			int range = (max - min + 1);
			return min + (input % range);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Reads a byte from the file, and returns a boolean
	 * @return
	 */
	public boolean getBoolean() {
		try {
			int input = this.file.read();
			return (input % 2) == 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
