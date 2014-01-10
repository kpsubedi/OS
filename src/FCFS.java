import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class FCFS implements SAInterface {
	
	private LinkedList<ProcessInfo> proceeList;
	//this list store the all processes or jobs
	
	
	private double totalWaitingTime = 0.0;
	private double waitingTime = 0.0;
	private double avgWaitingTime = 0.0;
	
	private double totalTurnAroundTime = 0.0;
	private double avgTurnAroundTime = 0.0;
	
	private double responseTime = 0.0;
	private double totalResponseTime = 0.0;
	private double cpuExecutionTime = 0.0;
	
	private double clock = 0.0;
	
	private double contextSwitchingTime = 0.0;
	
	
	public FCFS(LinkedList<ProcessInfo> list){
		proceeList = list;
		//this.computeParameters();
		
	}
	
	@Override
	public double getAvgTurnAroundTime() {
		// TODO Auto-generated method stub
		//System.out.println("FCFS average turn around time:");
		
		return this.avgTurnAroundTime;
	}

	@Override
	public double getWaitingTime() {
		// TODO Auto-generated method stub
		return this.avgWaitingTime;
	}
	public double getResponseTime()
	{
		return -1;
	}
	public double getThroughput()
	{
		return -1;
	}
	public double getProcessorUtilization()
	{
		return -1;
	}

	@Override
	public void computeParameters() {
		// TODO Auto-generated method stub
		List<ProcessInfo> temp = new ArrayList<ProcessInfo>(proceeList);
		
		clock = temp.get(0).getArrivalTime();
		
		while(temp.size()>0){
			ProcessInfo p1 = temp.remove(0);
			
			//if(clock < p1.getArrivalTime()){
				//clock = p1.getArrivalTime();
			//}
			
			waitingTime = clock - p1.getArrivalTime();
			
			responseTime = clock - p1.getArrivalTime();
			
			
			totalResponseTime+= responseTime;
			
			totalWaitingTime+= waitingTime; 
			
			clock = clock + p1.getTotalExecutionTime(); 
			
			cpuExecutionTime = cpuExecutionTime + p1.getTotalExecutionTime();
			
			//totalTurnAroundTime += (p1.getTotalExecutionTime()-p1.getArrivalTime());
			
			
			clock = clock + contextSwitchingTime;
			
			totalTurnAroundTime += (clock-p1.getArrivalTime());
		
		}
		
		clock = clock - contextSwitchingTime;
		
		//System.out.println("Total Turn Around Time:"+totalTurnAroundTime);
		
		double cpuUtilization = 1 * cpuExecutionTime /clock;
		
		
		avgTurnAroundTime = totalTurnAroundTime / proceeList.size();
		avgWaitingTime  = totalWaitingTime / proceeList.size();
		
		
		//DecimalFormat df = new DecimalFormat(".###");
		System.out.println("1. Average waiting time:"+avgWaitingTime);
		System.out.println("2. Average TurnAround time:"+avgTurnAroundTime);
		System.out.println("3. The CPU Utilization::"+cpuUtilization);
		System.out.println("4. Throughtput::"+proceeList.size()+"/"+clock);
		System.out.println("5. Response Time::"+totalResponseTime/proceeList.size());
		
		
		//System.out.println("Average TurnAround Time:"+avgTurnAroundTime);
		//System.out.println("Average Waiting Time:"+df.format(avgWaitingTime));
		
		
	}
	
	
	}
