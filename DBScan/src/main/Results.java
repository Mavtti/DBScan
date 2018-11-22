package main;

import java.util.List;

public class Results {
	private List<DataPoint> noises;
	private List<Cluster> clusters;
	
	public Results(List<DataPoint> noises, List<Cluster> clusters){
		this.noises = noises;
		this.clusters = clusters;
	}

	public List<DataPoint> getNoises() {
		return noises;
	}

	public List<Cluster> getClusters() {
		return clusters;
	}
	
	
}
