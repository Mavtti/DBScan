package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cluster {
	
	private List<DataPoint> clusterPoints;
	private int number;
	
	public Cluster(){
		clusterPoints = new ArrayList<DataPoint>();
	}
	
	public List<DataPoint> getClusterPoints(){
		return clusterPoints;
	}
	
	public void setNumber(int e){
		number = e;
	}
	
	public int getNumber(){
		return number;
	}
	
	
	
	public void connect(DataPoint point, List<DataPoint> neighbors, List<Integer> visitedPoints, DBScan dbs){
		point.setType(TypePoint.CORE);
		clusterPoints.add(point);
		int i = 0;
		
		while(i < neighbors.size()){
			DataPoint neighbor = neighbors.get(i); 
			TypePoint type = neighbor.getType();
			if(type == null){
				neighbor.setType(TypePoint.BORDER);	
				List<DataPoint> Nneighbors = dbs.getNeighbors(neighbor, dbs.getData());
				if(Nneighbors.size() > dbs.getN_min() ){
					merge(neighbors, Nneighbors);
				}
			}
            if (type != TypePoint.BORDER) {
                visitedPoints.add(dbs.getData().indexOf(neighbor));
                neighbor.setType(TypePoint.BORDER);
                clusterPoints.add(neighbor);
            }
            i++;
		}
	}
	
    private List<DataPoint> merge(List<DataPoint> neighbors, List<DataPoint> Nneighbors) {
        Set<DataPoint> oneSet = new HashSet<DataPoint>(neighbors);
        for (DataPoint item : Nneighbors) {
            if (!oneSet.contains(item)) {
                neighbors.add(item);
            }
        }
        return neighbors;
    }
     
}
