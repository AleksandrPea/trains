package parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class InternetConector {
	public static String conection(String s) throws IOException {
		URL url = new URL(s);
		InputStream in = url.openStream();
		InputStreamReader scan = new InputStreamReader(in);
		StringBuilder res = new StringBuilder();
		int ch;
		while((ch = scan.read()) != -1) 
			res.append((char) ch);
		
		//System.out.println(res);
		scan.close();
		return res.toString();
	}
	public static ArrayList<TStation> viewStation(String name) throws IOException {
		String s =InternetConector.conection("http://uz.gov.ua/passengers"
				+ "/timetables/suggest-station/?q=" + name);
		ArrayList<TStation> list = StringAnalyzer.getStationList(s);
		//System.out.println(list.size());
		//int j = 0;
		//for(int i = 0; i < list.size(); i++) {
			//TStation st = list.get(i);
			//System.out.println(st.getName() + "  " +
			//st.getCountry() + "  " + st.getId());
			//if(st.getCountry().equals("Україна")) j++;
		//}
		//System.out.println(j);
		return list;
	}
}
