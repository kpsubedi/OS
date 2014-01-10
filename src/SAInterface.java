
public interface SAInterface {
	
	public double getAvgTurnAroundTime();
	public double getWaitingTime();
	public double getResponseTime();
	public double getThroughput();
	public double getProcessorUtilization();
	
	public void computeParameters();
}
