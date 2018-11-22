package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBScan {
	
	private int n_min;
	private float eps;
	private List<DataPoint> data;
	
	// Constructor
	public DBScan(int n_min, float eps) throws Exception{
		if (eps < 0.0){
            throw new Exception("eps must be positive");
        }
        if (n_min < 0){
            throw new Exception("n_min must be positive");
        }
		this.n_min = n_min;
		this.eps = eps;
	}

	// Getters
	public int getN_min() {
		return n_min;
	}

	public float getEps() {
		return eps;
	}	
	
	public List<DataPoint> getData(){
		return data;
	}
	
	public void setData(List<DataPoint> e){
		this.data = e;
	}
	
	// Read CSV file
	public boolean parseCSV(String csvFilePath){
		List<DataPoint> myReturn = new ArrayList<DataPoint>();
		BufferedReader br = null;
        String line = "";
        Map<String,Integer> map = new HashMap<String,Integer>();
        int pos = 0;
        
        try {
        	
        	// We suppose that every csv file will start with the name of each data on the first row
            br = new BufferedReader(new FileReader(csvFilePath));
            List<String> info = new ArrayList<String>();
            line = br.readLine();
            String[] result = line.split(","); 
            while (pos != result.length){
            	info.add(result[pos]);
            	pos++;
            }
            System.out.println(info);
            int i = 0;
            int mapC = 0;
            
            while ((line = br.readLine()) != null) {
                result = line.split(","); // use comma as separator
                DataPoint data = new DataPoint();
                data.setNumber(i);
                pos = 0;
                try{
                    while(pos != result.length){
                    	float données;
                    	if(!isNumeric(result[pos])){
                    		if( map.get(result[pos]) != null) données = map.get(result[pos]);
                    		else{
                    			map.put(result[pos], mapC);
                    			données = mapC;
                    			mapC ++;
                    		}
                    		
                    	}
                    	else{
                    		données = Float.valueOf(result[pos]);
                    	}
                    	data.getDatas().add(Float.valueOf(données));
                    	pos += 1;
                    }
                }
                catch  ( IndexOutOfBoundsException e ) {}
                myReturn.add(data);
                i++;
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
       
		this.setData(myReturn);
		return true;
	}


	// Just a program to verify that the data are correctly saved inside the List<List<String>>
	public void showCSV(){
		for(int i = 0; i<data.size(); i++){
			for(Float s: data.get(i).getDatas()){
				System.out.print(s+" ");
			}
			System.out.println("");
		}
		System.out.println("OK!");
	}
	
	// DBScan algorithm
	public Results clusterDBScan(){
		
		List<Integer> visitedPoints = new ArrayList<Integer>();
		List<Cluster> clusters = new ArrayList<Cluster>();
		List<DataPoint> noises = new ArrayList<DataPoint>();
		System.out.println("Starting clustering ...");
		int number = 0;
		for( int i = 0; i < data.size(); i++){
			if(visitedPoints.contains(i)){
				continue;
			}
			
			List<DataPoint> neighbors = getNeighbors(data.get(i),data);
			System.out.println(showNumbers(neighbors));
			if(neighbors.size() < n_min){
				noises.add(data.get(i));
				data.get(i).setType(TypePoint.NOISE);
			}
			else{
				Cluster c = new Cluster();
				c.setNumber(number);
				number++;
				c.connect(data.get(i), neighbors, visitedPoints, this);
				clusters.add(c);
				System.out.println("New cluster");
			}
		}
		System.out.println("Clustering over ...");
		Results dico = new Results(noises,clusters);
		return dico;
	}
	
	public List<Integer> showNumbers(List<DataPoint> d){
		List<Integer> result = new ArrayList<Integer>();
		for(DataPoint p:d){
			result.add(p.getNumber());
		}
		return result;
	}
	
	public List<DataPoint> getNeighbors(DataPoint point, List<DataPoint> data) {
        final List<DataPoint> neighbors = new ArrayList<DataPoint>();
        for (DataPoint neighbor : data) {
            if (point != neighbor && neighbor.distanceFrom(point) <= eps) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }
	
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}

    public static void displayResults(Results results){
    	System.out.println("NOISE : "+results.getNoises().size());
    	for(DataPoint d: results.getNoises()){
    		System.out.print(d.getNumber()+"("+d.getType()+") ");
    	}
    	System.out.println("");
    	for(Cluster c: results.getClusters()){
    		System.out.println(c.getNumber()+" "+c.getClusterPoints().size());
    		for(DataPoint d: c.getClusterPoints())
    			System.out.print(d.getNumber()+"("+d.getType()+") ");
    		System.out.println("");
    	}
    }
    
	public static void main(String[] args) throws Exception {
		String csvFilePath = "D:/marc/Desktop/iris.csv";
		
		DBScan bd = new DBScan(5,(float) 0.5);
		bd.parseCSV(csvFilePath);

		Results clusters = bd.clusterDBScan();
		displayResults(clusters);
	}

}
