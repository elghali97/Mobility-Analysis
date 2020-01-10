import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class FileReader {
	
	public static void main(String[] args) throws IOException {
		// file in 
		 File file = new File("/Users/mazinea/Desktop/test/sorted.csv"); 
		 Scanner sc = new Scanner(file);
		 String line = ""; 
		 File fileOut = new File("/Users/mazinea/Desktop/test/sorted_november");
		 FileWriter writer = new FileWriter(fileOut);
		 while(sc.hasNextLine()) {
			 line = sc.nextLine();
			 String[] lineArr = line.split("\t");
			 if( isMonth(lineArr[1],10) ) {
				 writer.write(line+'\n');
			 }
		 }
		 writer.close();
	}
	
	public static boolean isMonth(String s, int month){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		Date date;
		try {
			date = formatter.parse(s);
			if(date.getYear() == 115 && date.getMonth() == month) {
            	return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
}
