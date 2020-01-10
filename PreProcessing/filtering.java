import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class filtering {

	public static void main(String[] args) throws IOException, ParseException {
		 File file = new File("/Users/mazinea/Desktop/test/sorted_december_random.csv"); 
		 File fileOut = new File("/Users/mazinea/Desktop/test/sorted_december_minutes");
		 filterByMinute(file,fileOut);
	}
	
	public static void filterByMinute(File file, File fileOut) throws IOException, ParseException {
		 Scanner sc = new Scanner(file);
		 FileWriter writer = new FileWriter(fileOut);
		 String linePrec = sc.nextLine(); 
		 String[] linePrecArr = linePrec.split("\t");
		 String idPrec = linePrecArr[0];
		 Date dPrec = getDate(linePrecArr[1]);
		 writer.write(linePrec+"\n");
		 String line = "";
		 while(sc.hasNextLine()) {
			 line = sc.nextLine();
			 String[] lineArr = line.split("\t");
			 String id = lineArr[0];
			 Date d = getDate(lineArr[1]);
			 if(!id.equals(idPrec) || d.getHours() != dPrec.getHours() || d.getMinutes() != dPrec.getMinutes()) {
				 idPrec = id; 
				 dPrec = d;
				 writer.write(line+"\n");
			 } 
		 }
	}
	
	public static Date getDate(String s) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		Date date = formatter.parse(s);
		return date;
	}
}
