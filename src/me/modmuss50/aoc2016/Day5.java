package me.modmuss50.aoc2016;

import java.security.MessageDigest;

/**
 * Created by modmuss50 on 06/12/16.
 */
public class Day5 {

	static String doorID = "uqwqemis";

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		int hashes = 0;
		String[] pass2 = new String[8];
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			String md5 = genMd5(doorID + i);
			if (md5.startsWith("00000")) {
				sb.append(md5.charAt(6 - 1));
				hashes++;
				System.out.println("Found " + hashes);

				try {
					int pos = Integer.parseInt(String.valueOf(md5.charAt(5)));
					String charat = String.valueOf(md5.charAt(6));
					if (pos < 8) {
						System.out.println(pos + ":" + charat);
						if (pass2[pos] == null || pass2[pos].isEmpty()) {
							pass2[pos] = charat;
						}

						StringBuilder builder = new StringBuilder();
						for (int p = 0; p < pass2.length; p++) {
							if (pass2[p] != null && !pass2[p].isEmpty()) {
								builder.append(pass2[p]);
							}
						}
						if (builder.toString().length() == 8) {
							System.out.println("Pass 2:" + builder.toString());
							break;
						}
					}
				} catch (NumberFormatException e) {

				}

			}
		}
		System.out.println("Password 1:" + sb.toString().substring(0, 8));
	}

	public static String genMd5(String md5) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] array = messageDigest.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

}
