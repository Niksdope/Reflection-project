package ie.gmit.sw;

public class Metric {
	private float indegree = 0;
	private float outdegree = 0;

	public void inIncrement(){
		indegree++;
	}
	
	public void outIncrement(){
		outdegree++;
	}
	
	// Robert C. Martin's metric for Positional Stability
	public float getStability(){
		try{
			return outdegree / (indegree + outdegree);
		}catch (ArithmeticException e){
			return 0;
		}
	}
	
	public float getIndegree() {
		return indegree;
	}

	public float getOutdegree() {
		return outdegree;
	}
}
