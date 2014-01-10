import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


public class Priority implements SAInterface{
	
	
	
	private LinkedList<ProcessInfo> myList;
	
	private double totalWaitingTime = 0.0;
	private double waitingTime = 0.0;
	private double avgWaitingTime = 0.0;

	private double totalTurnAroundTime = 0.0;
	private double turnAroundTime = 0.0;
	private double avgTurnAroundTime = 0.0;
	
	private double priorityCPUUtilization = 0.0;
	
	
	private double responseTime = 0.0;
	private double totalResponseTime = 0.0;
	
	private double cpuExecutionTime = 0.0;
	
	private double contextSwitichingTime = 2.0;
	private double clock = 0.0;
	
	
	public Priority(LinkedList<ProcessInfo> list)
	{
		myList = list;
	}

	@Override
	public double getAvgTurnAroundTime() {
		// TODO Auto-generated method stub
		return this.avgTurnAroundTime;
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
		return this.priorityCPUUtilization;
	}

	@Override
	public void computeParameters() {
		// TODO Auto-generated method stub
		
		LinkedList<ProcessInfo> tempList = new LinkedList<ProcessInfo>(myList);
		
		final Comparator<ProcessInfo> myPriorityComparator = new Comparator<ProcessInfo>() {
			@Override
			public int compare(ProcessInfo o1, ProcessInfo o2) {
				// TODO Auto-generated method stub
				return (int) (o1.getPriority()-o2.getPriority());
			}
		};
				
			
		//set clock is minimum process arrival time
		clock = tempList.get(0).getArrivalTime();
		
		//System.out.println("The initial clock:"+clock);
		
		while(tempList.size()>0)
		{
			
			LinkedList<ProcessInfo> candidateList = calculateCandidate(clock,tempList);
			
			if(candidateList.size()<=0)
			{
				clock = tempList.get(0).getArrivalTime();
				continue;
			}
			
			Collections.sort(candidateList, myPriorityComparator);
			
			//for(ProcessInfo ll: candidateList)
				//System.out.println(ll.getProcessName());
			
			ProcessInfo p = candidateList.remove(0);
			
		    waitingTime = clock - p.getArrivalTime();
			
			responseTime = clock - p.getArrivalTime();
			totalResponseTime = totalResponseTime + responseTime;
			
			//System.out.println(p.getProcessName()+"::"+responseTime);
		    totalWaitingTime = totalWaitingTime + waitingTime;
			
			clock = clock + p.getTotalExecutionTime();
			
			cpuExecutionTime = cpuExecutionTime + p.getTotalExecutionTime();
			
			clock = clock + contextSwitichingTime;
			
			turnAroundTime = clock - p.getArrivalTime();
			
			totalTurnAroundTime = totalTurnAroundTime + turnAroundTime;
					
			//System.out.println(p.getProcessName()+":wt:"+waitingTime+"\t");
			//System.out.println(p.getProcessName()+":finishedtime:"+clock);
			//System.out.println(p.getProcessName()+":turn around:"+turnAroundTime);
			
			tempList.remove(p);
			
		}
		
		clock = clock - contextSwitichingTime;
		
		
		
		avgWaitingTime = totalWaitingTime / myList.size();
		avgTurnAroundTime = totalTurnAroundTime / myList.size();
		
		double cpuUtilization = 1 * cpuExecutionTime / clock;
		
		System.out.println("1. Average waiting time:"+avgWaitingTime);
		System.out.println("2. Average TurnAround time:"+avgTurnAroundTime);
		System.out.println("3. The CPU Utilization::"+cpuUtilization);
		System.out.println("4. Throughtput::"+myList.size()+"/"+clock);
		System.out.println("5. Response Time::"+totalResponseTime/myList.size());
		
		//System.out.println("The CPU Execution Time:"+cpuExecutionTime);
		//System.out.println("The clock time:"+clock);
		
		
		}

	private LinkedList<ProcessInfo> calculateCandidate(double clock,
			LinkedList<ProcessInfo> tempList) {
		// TODO Auto-generated method stub
		
		LinkedList<ProcessInfo> privateList = new LinkedList<ProcessInfo>();
		double myClock = clock;
		
		for(ProcessInfo myP:tempList)
		{
			if(myP.getArrivalTime()<=myClock)
				privateList.add(myP);
		}
		
		
		return privateList;
	}


}
