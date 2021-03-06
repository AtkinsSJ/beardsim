package uk.co.samatkins;

public class Base64 {
	
	private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijklmnopqrstuvwxyz" + "0123456789" + "+/";
	private static final int SPLIT_LINES_AT = 76;
	
	public static String encode(String source) {
		StringBuilder builder = new StringBuilder();
		
		int length = source.getBytes().length;
		
		// Calculate padding
		int paddingLength = (3 - (length % 3)) % 3;
		length += paddingLength;
		
		// Pad array
		byte[] byteArray = new byte[length]; 
		System.arraycopy(source.getBytes(), 0, byteArray, 0, length-paddingLength);
		
		// Encode chars
		int j;
		for (int i=0; i<byteArray.length; i+=3) {
			j = ((byteArray[i] & 0xff) << 16) +
				((byteArray[i + 1] & 0xff) << 8) + 
				(byteArray[i + 2] & 0xff);
			builder.append(CHARS.charAt( (j >> 18) & 0x3f))
					.append(CHARS.charAt( (j >> 12) & 0x3f))
					.append(CHARS.charAt( (j >> 6) & 0x3f))
					.append(CHARS.charAt( j & 0x3f));
		}
		
		// Split into lines and add ==s
		String result = builder.toString();
		return splitLines(result.substring(0, result.length() - paddingLength)
				+ "==".substring(0, paddingLength));
	}
	
	public static void main(String[] args) {
		String encoded = Base64.encode(args[0]);
		System.out.println(encoded);
		System.out.println(Base64.decode(encoded));
		
	}
	
	private static String splitLines(String input) {
		StringBuilder builder = new StringBuilder();
		for (int i=0; i < input.length(); i+= SPLIT_LINES_AT) {
			builder.append(input.substring(i, Math.min(input.length(), i + SPLIT_LINES_AT)))
					.append("\r\n");
			
		}
		return builder.toString();
	}
	
	public static String decode(String source) {
		StringBuilder builder = new StringBuilder();
		
		// Strip-out the line breaks
		for (int i=0; i<source.length(); i+= SPLIT_LINES_AT + 2) {
			int remaining = source.length() - i;
			builder.append(source.substring(i, i + Math.min(remaining, SPLIT_LINES_AT)));
		}
		
		String singleLine = builder.toString();
		builder = new StringBuilder();
		int val, k;
		for (int i=0; i<singleLine.length(); i+=4) {
			val = 0;
			for (int j=0; j<=3; j++) {
				k = CHARS.indexOf(singleLine.charAt(i+j));
				if (k == -1) {
					break;
				}
				val += (k << (18 - 6*j));
			}
			
			for (int j=16; j>=0; j-=8) {
				char c = (char) ((val >> j) & 0xff);
				// Ignore blank chars on the end
				if (c == '\0') {
					continue;
				}
				
				builder.append(c);
			}
		}
		
		return builder.toString();
	}
}
