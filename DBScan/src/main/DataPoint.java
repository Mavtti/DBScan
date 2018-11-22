package main;

import java.util.ArrayList;
import java.util.List;

public class DataPoint {
	private List<Float> datas; 
	private TypePoint type;
	private int number;
	
	public DataPoint(){
		datas = new ArrayList<Float>();
		type = null;
	}

	public List<Float> getDatas() {
		return datas;
	}

	public void setNumber(int e){
		number = e;
	}
	
	public int getNumber(){
		return number;
	}
	
	public TypePoint getType() {
		return type;
	}
	
	public void setType(TypePoint e){
		type = e;
	}

	public float distanceFrom(DataPoint point) {
		float distance = 0;
		for( int i = 0 ; i < point.getDatas().size(); i++){
			distance += Math.pow(datas.get(i)-point.getDatas().get(i),2);
		}
		distance = (float)Math.sqrt(distance);
		System.out.println(distance);
		return distance;
	}
	
	
}
