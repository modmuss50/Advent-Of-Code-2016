package me.modmuss50.aoc2016;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by modmuss50 on 06/12/16.
 */
public class Day6 {

	static HashMap<Integer, Coloum> coloumHashMap;

	public static void main(String[] args) throws IOException {
		InputStream is = new FileInputStream("day6Input.txt");
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));

		String line = buf.readLine();
		int lineSize = line.length();

		while (line != null) {
			for (int i = 0; i < line.length(); i++) {
				if (coloumHashMap == null) {
					coloumHashMap = new HashMap<>();
					for (int j = 0; j < line.length(); j++) {
						coloumHashMap.put(j, new Coloum());
					}
				}
				Coloum coloum = coloumHashMap.get(i);
				int count = 1;
				if (coloum.count.containsKey(line.charAt(i))) {
					count += coloum.count.get(line.charAt(i));
					coloum.count.remove(line.charAt(i));
				}
				coloum.count.put(line.charAt(i), count);
				System.out.println("Line:" + line + " Char:" + line.charAt(i) + " charpos:" + i + " count:" + count);
			}
			line = buf.readLine();
		}

		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		for (int i = 0; i < lineSize; i++) {
			int popularCount = 0;
			char popularChar = 0;
			int rareCount = 100;
			char rareChar = 100;
			Coloum coloum = coloumHashMap.get(i);
			for (Map.Entry<Character, Integer> entry : coloum.count.entrySet()) {
				char chara = entry.getKey();
				int count = entry.getValue();
				if (count > popularCount) {
					popularCount = count;
					popularChar = chara;
				}
				if (count < rareCount) {
					rareCount = count;
					rareChar = chara;
				}
			}
			sb.append(popularChar);
			sb2.append(rareChar);
		}
		System.out.println(sb.toString());
		System.out.println(sb2.toString());

	}

	private static class Coloum {
		public HashMap<Character, Integer> count = new HashMap<>();
	}

}
