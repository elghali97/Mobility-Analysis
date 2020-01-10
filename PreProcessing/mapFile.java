import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class mapFile {
	public static void main(String[] args) throws IOException {
		 File file = new File("/Users/mazinea/Desktop/test/sorted_december_minutes"); 
		 Scanner sc = new Scanner(file);
		 String line = ""; 
		 File fileOut = new File("/Users/mazinea/Desktop/test/map_random_minutes");
		 FileWriter writer = new FileWriter(fileOut);
		 HashMap<Integer,Integer> myMap = new HashMap<Integer,Integer>();
		 while(sc.hasNextLine()) {
			 line = sc.nextLine();
			 String[] lineArr = line.split("\t");
			 int value = Integer.parseInt(lineArr[0]);
			 if(myMap.containsKey(value))
			 	myMap.put(value, myMap.get(value)+1);
		 	 else 
		 		myMap.put(value, 1);
		 }
		 int sum = 0; 
		 for(Integer inte : myMap.keySet()) {
			writer.write(inte+" "+myMap.get(inte)+'\n');
			sum+= myMap.get(inte); 
		 }	
		 System.out.println(sum);
		 writer.close();
	}
}
