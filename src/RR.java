import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RR implements SAInterface {

	private LinkedList<ProcessInfo> myList;

	private double quantum = 4;
	private double waitingTime = 0.0;
	private double avgWaitingTime = 0;
	
	private double clock = 0.0;
	private double cpuExecutionTime = 0.0;
	private double contextSwitchingTime = 0.0;
	
	private double turnAroundTime = 0.0;
	private double totalTurnAroundTime = 0.0;
	private double avgTurnAroundTime = 0.0;
	
	private double responseTime = 0.0;
	
	//private HashMap<String, Double> turnHash = null;

	private double totalResponseTime = 0.0;
	

	public RR(LinkedList<ProcessInfo> list) {
		myList = list;
	}
	@Override
	public double getAvgTurnAroundTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWaitingTime() {
		// TODO Auto-generated method stub
		return this.avgWaitingTime;
	}

	@Override
	public double getResponseTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getThroughput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getProcessorUtilization() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getTimeSlice() {
		return quantum;
	}

	public void setTimeSlice(double timeSlice) {
		this.quantum = timeSlice;
	}
	
	

	
	public void computeParameters() {
		// TODO Auto-generated method stub
		
		List<ProcessInfo> tempList = new LinkedList<ProcessInfo>(myList);
		
		HashMap<String, Double> originalProcessArrivalTime = new HashMap<String, Double>();
		
		for(ProcessInfo P:tempList)
			originalProcessArrivalTime.put(P.getProcessName(), P.getArrivalTime());
		
		
		//for(ProcessInfo p: tempList)
			//System.out.println(p.getProcessName()+":"+p.getArrivalTime()+":"+p.getBurstTime()+":"+p.getPriority()+":"+p.getTotalExecutionTime());
		
		double minArrivalTime = tempList.get(0).getArrivalTime();
		
		clock = minArrivalTime; 			
	
		turnAroundTime = 0.0;

		HashMap<String, Double> myHash = new HashMap<String, Double>();
		

		while (tempList.size() > 0) {

			List<ProcessInfo> toBeRemoved = new ArrayList<ProcessInfo>();
			
			for (int i = 0; i < tempList.size(); i++) {
				
				ProcessInfo p = tempList.get(i);
				
				// ///
				
				if (p.getArrivalTime() > clock)
					continue;
				// clock = p.getArrivalTime();
				else {
					// calculate waiting time
					
					waitingTime = clock - p.getArrivalTime() ;
					
					
					if(p.getCount()==0)
					{
						p.setCount(1);
						
						responseTime = clock - p.getArrivalTime();
						
						totalResponseTime = totalResponseTime + responseTime;
						
						//System.out.println(p.getProcessName()+":response Time:"+responseTime);	
						//System.out.println("The total response time:"+totalTurnAroundTime);
						
					}
					//response time for first process
					
					//turnAroundTime = waitingTime + p.getTotalExecutionTime();
					
					//System.out.println(p.getProcessName()+":"+p.getArrivalTime()+":"+waitingTime);

					if (myHash.containsKey(p.getProcessName())) {
						double value = myHash.get(p.getProcessName());
						value += waitingTime;
						myHash.put(p.getProcessName(), value);
					} else {
						myHash.put(p.getProcessName(), waitingTime);
					}
					
					
					if (p.getTotalExecutionTime() <= quantum) {
																				
						clock = clock + p.getTotalExecutionTime();
						cpuExecutionTime = cpuExecutionTime + p.getTotalExecutionTime();
						//System.out.println("clock:"+clock);
						
						//i need p initial arrival time
						
						//String name = p.getProcessName();
														
						turnAroundTime = clock - originalProcessArrivalTime.get(p.getProcessName());
						
								
						totalTurnAroundTime = totalTurnAroundTime + turnAroundTime;
						//System.out.println(p.getProcessName()+":turn around time:"+turnAroundTime);
								
						toBeRemoved.add(p); 
						
					} 
					else {
											
						clock += quantum;
						cpuExecutionTime = cpuExecutionTime + quantum;
						p.setTotalExecutionTime(p.getTotalExecutionTime() - quantum);
						p.setArrivalTime(clock);

					}
					
					if(tempList.size()>1)
					{
						clock = clock + contextSwitchingTime;
					}
					

				}
				//if(p.getProcessName().equalsIgnoreCase("P2"))
				
				//System.out.println("Context Switch");
				//System.out.println(p.getProcessName()+"--at:" +p.getArrivalTime()+", wt:"+myHash.get(p.getProcessName()));
				//System.out.println(turnAroundTime);
			}
			tempList.removeAll(toBeRemoved);
			//System.out.println("Clock: "+clock+", TempList:"+tempList.size()+",ToB:"+toBeRemoved.size());
			
			
		}// while
		
		//clock = clock - contextSwitchingTime;
		
		//calculate average waiting time
		
		Set<String> myKeys = myHash.keySet();
		double temp=0;
		for(String key:myKeys){
			
			//System.out.println(key+"-->"+myHash.get(key));
			temp = temp + myHash.get(key);
		}
		
		this.avgWaitingTime = temp / myList.size();
				
		System.out.println("1. Average waiting time:"+avgWaitingTime);
		System.out.println("2. Average TurnAround time:"+totalTurnAroundTime/myList.size());
		System.out.println("3. The CPU Utilization::"+1*cpuExecutionTime/clock);
		System.out.println("4. Throughtput::"+myList.size()+"/"+clock);
		System.out.println("5. Response Time::"+totalResponseTime/myList.size());
		
		

	}
	
	
	

}
