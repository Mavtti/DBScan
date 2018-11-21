package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBScan {
	
	private List<List<String>> datas;
	private int n_min;
	private int eps;
	
	public DBScan(List<List<String>> datas, int n_min, int eps){
		this.datas = datas;
		this.n_min = n_min;
		this.eps = eps;
	}

	
	public static List<List<String>> parseCSV(List<String> Inputs, String csvFilePath, String indexClass){
		List<List<String>> myReturn = new ArrayList<List<String>>();
		BufferedReader br = null;
        String line = "";
        int pos = 0;

        for(String input: Inputs){
        	List<String> init = new ArrayList<String>();
        	init.add(input);
        	myReturn.add(init);
        }
        
        if(!Inputs.isEmpty()) Inputs.remove("class");

        try {
            br = new BufferedReader(new FileReader(csvFilePath));
            while ((line = br.readLine()) != null) {
                String[] result = line.split(","); // use comma as separator
                while(pos != result.length){
                	try {
                		myReturn.get(pos);
                	} catch ( IndexOutOfBoundsException e ) {
                		List<String> init = new ArrayList<String>();
                    	init.add(String.valueOf(pos));
                    	myReturn.add(init);
                	}
                	myReturn.get(pos).add(result[pos]);
                	pos += 1;
                }
                myReturn.get(Integer.parseInt(indexClass)).set(0,"class");
                pos = 0;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return myReturn;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
