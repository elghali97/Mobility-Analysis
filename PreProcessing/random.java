import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class random {
	
	public static void main(String[] args) throws IOException {
		 File file = new File("/Users/mazinea/Desktop/test/sorted_december"); 
		 File fileOut = new File("/Users/mazinea/Desktop/test/sorted_december_random");
		 HashMap<Integer,Integer> myMap = countPerId(file);
		 filteringNumberOfRes(file,fileOut,myMap);
	}
	
	
	public static HashMap<Integer,Integer> countPerId(File filename) throws IOException{
		 Scanner sc = new Scanner(filename);
		 String line = ""; 
		 File fileOut = new File("/Users/mazinea/Desktop/test/map_random");
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
		 for(Integer inte : myMap.keySet()) {
			 writer.write(inte+" "+myMap.get(inte)+'\n');
		 }	
		 writer.close();
		 return myMap;
	}
	
	public static void filteringNumberOfRes(File filename, File fileOut, HashMap<Integer,Integer> myMap) throws IOException {
		 Scanner sc = new Scanner(filename);
		 String line = ""; 
		 FileWriter writer = new FileWriter(fileOut);
		 int step; 
		 int i = 0; 
		 while(sc.hasNextLine()) {
			 line = sc.nextLine();
			 int id = Integer.parseInt(line.split("\t")[0]);
			 int count = myMap.get(id);
			 if(count < 10000) 
				 step = 0; 
			 else if(count < 100000)
				 step = 1; 
			 else if (count < 500000)
				 step = 5;
			 else
				 step = 10;
			 // starting the filtering function 
			 if( step != 0 && i%step == 0) {
					 writer.write(line+'\n');
			 }
			 i++;
		 }
		 writer.close();
	}
	
	
}
