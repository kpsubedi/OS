import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;



public class SimulatorMain {
	
	
	//private static LinkedList<ProcessInfo> allProcessInfo1 = new LinkedList<ProcessInfo>();
	//private static Iterator<ProcessInfo> myIterator;
	private static LinkedList<ProcessInfo> allProcessInfo = new LinkedList<ProcessInfo>();
	private static Hashtable <String, LinkedList> processGroups = null;
	
	public static void main(String[] args)
	{
		System.out.println("Welcome to Discrete Event Simulator to Model a CPU Schedule");
		String file = "TestData/myProcess.txt";
		if(args.length>0)
		{
			file = args[0];
		}
		
		DESUtils myUtils = new DESUtils();
		
		//allProcessInfo list stores ProcessInfo objects
		//allProcessInfo1 = myUtils.readProcessInfo();
		

		final Comparator<ProcessInfo> myArrivalProceeeComparator = new Comparator<ProcessInfo>() {
			@Override
			public int compare(ProcessInfo o1, ProcessInfo o2) {
				// TODO Auto-generated method stub
				return (int) (o1.getArrivalTime() - o2.getArrivalTime());
			}
		};
		
		
		processGroups = myUtils.readProcessInfo(file);
		
		allProcessInfo = processGroups.get("#T6");
		
		//allProcessInfo = myUtils.readProcessInfo();
		
		Collections.sort(allProcessInfo, myArrivalProceeeComparator);
		
		//System.out.println("All Admitted Processes:");
		//System.out.println("Process Name"+"\t"+"Arrival Time"+"\t"+"Burst Time"+"\t"+"Priority"+"\t"+"Total Execution Time");
		//displayProcessLinkedList();
		
		/*Scanner sc = new Scanner(System.in);
		
		System.out.println("CPU SCHEDULER SIMULATION");
		
		System.out.println("1. FCFS");
		System.out.println("2. Non Preemptive SJF");
		System.out.println("3. Non Preemptive Priority");
		System.out.println("4. RR");
		System.out.println("5. Non Preemptive Multilevel strict priority queue");
		System.out.println("6. Multilevel feedback queue");
		System.out.println("7. Exit");
		
		int choice;
		
		while(true)
		{
		
			choice = sc.nextInt();
			
			switch(choice)
			{
			case 1:*/ 
		
				//System.out.println("FCFS");
				//FCFS myFCFS = new FCFS(allProcessInfo);
				//myFCFS.computeParameters();
				//myFCFS.getAvgTurnAroundTime();
				//double avgWaitingTime = myFCFS.getWaitingTime();
				//System.out.println("Average Waiting Time of FCFS:"+avgWaitingTime);
				//double avgTurnAroundTime = myFCFS.getAvgTurnAroundTime();
				//System.out.println("Average turn around Time of FCFS:"+avgTurnAroundTime);
				//break;
				
			//case 2:

				//System.out.println("Welcome to SJF");
				//SJF mySJF = new SJF(allProcessInfo);
				//display process list in SJF
				//mySJF.display();
				//mySJF.computeParameters();
				//break;
				
			//case 3:
				//System.out.println("I am from Priority");
				//Priority myPriority = new Priority(allProcessInfo);
				//myPriority.computeParameters();
				
				//System.out.println("The Average Waiting Time:"+myPriority.getWaitingTime());
				//System.out.println("The Average Turn Around Time:"+myPriority.getAvgTurnAroundTime());
				//System.out.println("The CPU UTilization:"+myPriority.getProcessorUtilization());
				//break;
				
			//case 4:
				//done 
				
				//System.out.println("RR");
				//RR myRoundRobin = new RR(allProcessInfo);
				//myRoundRobin.computeParameters();
				//double avgWaitingRR = myRoundRobin.getWaitingTime();
				//System.out.println("The Average Waiting Time of RR:"+avgWaitingRR);
				//break;
				
				
			//case 5:
					//not done
				
					System.out.println("Multilevel queue");
					MultiLevelQueue multiQueue = new MultiLevelQueue(allProcessInfo);
					multiQueue.computeParameters();
					
					//break;
					
			//case 6:
					//System.out.println("MultiLevel Feedback queue");
					//MultiLevelFeedBack multiFeedBack = new MultiLevelFeedBack(allProcessInfo);
					//multiFeedBack.computeParameters();
					//multiFeedBack.displayWaitingTime();
					//break;
					
			//case 7:
				//	System.out.println("Exit");
					//System.exit(0);
				
					
			//default:
				//	System.out.println("default");
					
					
			
			}
		
		
		
		
		
		
		
		//System.out.println();
		//System.out.println();
		
		//RR myRoundRobin = new RR(allProcessInfo);
		//myRoundRobin.computeParameters();
		//double avgWaitingRR = myRoundRobin.getWaitingTime();
		//System.out.println("The Average Waiting Time of RR:"+avgWaitingRR);
		
		
		
		//MultiLevelFeedBack multiFeedBack = new MultiLevelFeedBack(allProcessInfo);
		//
		//multiFeedBack.displayFromMulti();
		//multiFeedBack.computeParameters();
		//multiFeedBack.displayWaitingTime();
		
		
		
	
	
	
	private static void displayProcessLinkedList()
	{
		//System.out.println("Procee Name:"+"\t"+"Arrival Time:"+"\t"+"Burst Time"+"\t"+"Priority"+"\t"+"Total Execution Time:");
			for(ProcessInfo pi: allProcessInfo)
			{
				
				System.out.println(pi.getProcessName()+"\t\t"+pi.getArrivalTime()+"\t\t"+pi.getBurstTime()+"\t\t"+pi.getPriority()+"\t\t"+pi.getTotalExecutionTime());
				
				
			}
			
		}
		
		
	//}

}
