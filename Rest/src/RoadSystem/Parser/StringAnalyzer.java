package Parser;

import java.util.ArrayList;

public class StringAnalyzer {
	public static ArrayList<TStation> getStationList(String s) {
		ArrayList<TStation> res = new ArrayList<TStation>();
		String name = null;
		String id = null;
		String country = null;
		int i = 0;
		while(i < s.length()) {
			while(i < s.length() && s.charAt(i) != '"') i++;
			i++;
			int j = i + 1;
			while(j < s.length() && s.charAt(j) != '(') j++;
			j--;
			name = s.substring(i, j);
			i = j + 2;
			j = j + 3;
			while(j < s.length() && s.charAt(j) != ')') j++;
			country = s.substring(i, j);
			i = j + 2;
			j = j + 3;
			while(j < s.length() && s.charAt(j) != '"') j++;
			id = s.substring(i, j);
			i = j + 3;
			res.add(new TStation(name,id ,country));
		}
		return res;
	}
}
