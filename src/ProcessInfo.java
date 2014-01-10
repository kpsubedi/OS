
public class ProcessInfo {
	
	private String processName;
	private double arrivalTime;
	private double burstTime;
	private double priority;
	private double totalExecutionTime;
	
	private int count;
	
	
	public ProcessInfo()
	{
		processName = "";
		arrivalTime = 0.0;
		burstTime = 0.0;
		priority = 0.0;
		totalExecutionTime = 0.0;
		count = 0;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public double getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public double getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(double burstTime) {
		this.burstTime = burstTime;
	}

	public double getPriority() {
		return priority;
	}

	public void setPriority(double priority) {
		this.priority = priority;
	}

	public double getTotalExecutionTime() {
		return totalExecutionTime;
	}

	public void setTotalExecutionTime(double totalExecutionTime) {
		this.totalExecutionTime = totalExecutionTime;
	}
	
	public int getCount()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	

}
