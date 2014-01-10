import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


public class MultiLevelQueue implements SAInterface {
	
	private LinkedList<ProcessInfo> processList;
	
	
	private LinkedList<ProcessInfo> highPriorityQueue;
	private LinkedList<ProcessInfo> middlePriorityQueue;
	private LinkedList<ProcessInfo> lowPriorityQueue;
	
	//variable to store priority
	
	private double maxPriority = 0.0;
	private double minPriority = 0.0;
	
	private double waitingTime = 0.0;
	private double totalWaitingTime = 0.0;
	private double avgWaitingTime = 0.0;
	
	private double clock = 0.0;
	private double cpuExecutionTime = 0.0;
	
	private double contextSwitchingTime = 0.0;
	
	private double turnAroundTime = 0.0;


	private double totalTurnAroundTime = 0.0;
	
	public MultiLevelQueue(LinkedList<ProcessInfo> list)
	{
		processList = list;
		
	}
	
	

	@Override
	public double getAvgTurnAroundTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWaitingTime() {
		// TODO Auto-generated method stub
		return 0;
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

	@Override
	public void computeParameters() {
		// TODO Auto-generated method stub
		
			maxPriority = getMaxPriority();
			
			//System.out.println("The maximum Priority in Process List:"+maxPriority);
			
			minPriority = getMinPriority();
			
			//System.out.println("The minimum Priority in Process List:"+minPriority);
			
			LinkedList<ProcessInfo> tempList = new LinkedList<ProcessInfo>(processList);
			
			
			/*final Comparator<ProcessInfo> myPriorityComparator = new Comparator<ProcessInfo>() {
				@Override
				public int compare(ProcessInfo o1, ProcessInfo o2) {
					// TODO Auto-generated method stub
					return (int) (o1.getPriority()-o2.getPriority());
				}
			};*/
			
			
			
			//Collections.sort(tempList, myPriorityComparator);
			

			for(ProcessInfo p:tempList)
			{
				System.out.println(p.getProcessName()+"Priority:"+p.getPriority());
				
			}
			
			
			highPriorityQueue = new LinkedList<ProcessInfo>();
			//this queue with priority greater or equal than 10
			middlePriorityQueue = new LinkedList<ProcessInfo>();
			//this queue with priority less than 10 and greater or equal to 5 
			lowPriorityQueue = new LinkedList<ProcessInfo>();
			//this queue store priority less than 5
			
			//System.out.println(tempList.size());
			
				
			for(ProcessInfo myP:tempList)
			{
				if(myP.getPriority()>=10)
				{
					highPriorityQueue.add(myP);
				}
				else if(myP.getPriority()>=5)
				{
					middlePriorityQueue.add(myP);
				}
				else
				{
					lowPriorityQueue.add(myP);
				}
				
			}
			
			System.out.println("high prioritu");
			for(ProcessInfo p:highPriorityQueue)
			System.out.println(p.getProcessName());
			
			System.out.println("middle priority");
			for(ProcessInfo p:middlePriorityQueue)
				System.out.println(p.getProcessName());
			
			System.out.println("low priorit");
			for(ProcessInfo p:lowPriorityQueue)
				System.out.println(p.getProcessName());
			
		System.out.println("the size of queue:"+highPriorityQueue.size());
		System.out.println("the size of queueu"+middlePriorityQueue.size()+":lll:"+lowPriorityQueue.size());
			
			
			//clock = tempList.get(0).getArrivalTime();
					
		
			while(highPriorityQueue.size()>0||middlePriorityQueue.size()>0||lowPriorityQueue.size()>0)
			{			
				
				executeHighPriority();
						
			}
			
			//result display
			
			System.out.println("Total Waiting Time:"+totalWaitingTime);
			System.out.println("The Average Waiting Time:"+totalWaitingTime/processList.size());
			System.out.println("The clock:"+clock);
			System.out.println("The CPU Execution time:"+cpuExecutionTime);
			System.out.println("The Throughput is:"+processList.size()+"/"+clock);
			System.out.println("The CPU Utilization:"+1 * cpuExecutionTime/clock);
	
		
			}
	
	private void executeHighPriority() {
		// TODO Auto-generated method stub
		
                 if(highPriorityQueue.size()>0)
                 {
                	 
                	 //process the queue
                	 
                	 clock = processList.get(0).getArrivalTime();
                	 
                	 ProcessInfo highP = highPriorityQueue.get(0);
                	 
                	 //System.out.println(highP.getProcessName()+":"+highP.getTotalExecutionTime());
                	 
                	 if(clock<highP.getArrivalTime())
                	 {
                		 //process middle priority queue
                		 
                		 executeMiddlePriority();
                		 
                	 }
                	 else
                	 {
                		 // adjust clock according to current process
                		 //kick out process
                		 //cpu assign and waiting time calculate
                		 
                		
                		 waitingTime = clock - highP.getArrivalTime();
                		 
                		 totalWaitingTime = totalWaitingTime + waitingTime;
                		 
                		 cpuExecutionTime = cpuExecutionTime + highP.getTotalExecutionTime();
                		 
                		 clock = clock + highP.getTotalExecutionTime() + contextSwitchingTime;
                		 
                		 turnAroundTime = clock - highP.getArrivalTime();
                		 System.out.println(highP.getProcessName()+":turn around:"+turnAroundTime);
                		 totalTurnAroundTime = totalTurnAroundTime + turnAroundTime;
                		 
                		 //System.out.println(highP.getProcessName()+":"+waitingTime);
                		 
                		 highPriorityQueue.remove(highP);
                		 
                	 }
                 }
                 else if(highPriorityQueue.size()==0)
                 {
                	 //execute second queue
                	 executeMiddlePriority();
                 }
                 
				
		
		
	}



	private void executeMiddlePriority() {
		// TODO Auto-generated method stub
		
		if(middlePriorityQueue.size()>0)
		{
			ProcessInfo middleP = middlePriorityQueue.get(0);
			//System.out.println(middleP.getProcessName()+":"+middleP.getTotalExecutionTime());
			if(clock<middleP.getArrivalTime())
			{
				//process low priority queues
				executeLowPriority();
				
			}
			else
			{
				//adjust clock and calculate waiting time
				//run process and kick out
				waitingTime = clock - middleP.getArrivalTime();
				totalWaitingTime = totalWaitingTime + waitingTime;
				
				//System.out.println(middleP.getProcessName()+"::"+waitingTime);
				
				cpuExecutionTime = cpuExecutionTime + middleP.getTotalExecutionTime();
				clock = clock + middleP.getTotalExecutionTime() + contextSwitchingTime;
				
				turnAroundTime = clock - middleP.getArrivalTime();
       		    System.out.println(middleP.getProcessName()+":turn around:"+turnAroundTime);
       		    totalTurnAroundTime = totalTurnAroundTime + turnAroundTime;
       		    
				middlePriorityQueue.remove(middleP);
			}
		}
		
		
		
	}



	private void executeLowPriority() {
		// TODO Auto-generated method stub
		
		if(lowPriorityQueue.size()>0)
		{
			ProcessInfo lowP = lowPriorityQueue.get(0);
			System.out.println(lowP.getProcessName()+":"+lowP.getTotalExecutionTime()+":"+lowP.getPriority());
			if(lowP.getArrivalTime()<clock)
			{
				//proess kick off and kick out
				//clock increase
				waitingTime = clock - lowP.getArrivalTime();
				
				
				totalWaitingTime = totalWaitingTime + waitingTime;
				
				cpuExecutionTime = cpuExecutionTime + lowP.getTotalExecutionTime();
				clock = clock + lowP.getTotalExecutionTime()+contextSwitchingTime;
				
				turnAroundTime = clock - lowP.getArrivalTime();
       		    System.out.println(lowP.getProcessName()+":turn around:"+turnAroundTime);
       		    
       		    totalTurnAroundTime = totalTurnAroundTime + turnAroundTime;
       		    
				lowPriorityQueue.remove(lowP);
			}
			
		}
		
		
		
		
	}



	public double getMaxPriority()
	{
		
		double maxPriority = 0.0;
		
		for(ProcessInfo maxP:processList)
		{
			if(maxP.getPriority()>maxPriority)
				maxPriority = maxP.getPriority();
		}
	
		
		return maxPriority;
	
	}
	
	public double getMinPriority()
	{
		double minPriority = processList.get(0).getPriority();
		
		for(ProcessInfo p: processList)
		{
			if(p.getPriority()<minPriority)
				
				minPriority = p.getPriority();
					
		}
		
		return minPriority;
				
	}
	
	
	
	//public void processDisplay()
	//{
		//for(ProcessInfo p: processList)
		//{
			//System.out.println(p.getProcessName()+":AT"+p.getArrivalTime()+":BT"+p.getBurstTime()+":Prio"+p.getPriority()+":TET"+p.getTotalExecutionTime());
		//}
	//}

}
