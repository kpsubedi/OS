import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class SJF implements SAInterface {

	private LinkedList<ProcessInfo> myList;

	private double waitingTime = 0.0;
	private double avgWaitingTime = 0.0;
	private double totalWaitingTime = 0.0;
	
	private double clock = 0.0;
	private double contextSwitchingTime = 0.0;
	private double cpuExecutionTime = 0.0;
	
	private double responseTime = 0.0;
	private double totalResponseTime = 0.0;
	private double turnAroundTime = 0.0;
	private double totalTurnAroundTime = 0.0;
	
	

	public SJF(LinkedList<ProcessInfo> list) {
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

		LinkedList<ProcessInfo> tempList = new LinkedList<ProcessInfo>(myList);
		
		final Comparator<ProcessInfo> myComparator = new Comparator<ProcessInfo>() {
			@Override
			public int compare(ProcessInfo o1, ProcessInfo o2) {
				// TODO Auto-generated method stub
				return (int) (o1.getTotalExecutionTime() - o2.getTotalExecutionTime());
			}
		};

		//for (ProcessInfo p : tempList)
			//System.out.println(p.getProcessName() + "-" + p.getArrivalTime()
				//	+ "-" + p.getBurstTime() + "-" + p.getPriority() + "-"
				//	+ p.getTotalExecutionTime());

		
		
		 clock = tempList.get(0).getArrivalTime();
		//set this clock to minimum arrival time 
		
		//double waitingTime = 0.0;

		while (tempList.size() > 0) {


			LinkedList<ProcessInfo> tempList1 = remainingProceeList(clock, tempList);
			if(tempList1.size()<=0){
				clock = tempList.get(0).getArrivalTime();
				continue;
			}
			
			// tempList1 holds the process with arrival timeless then clock;
			Collections.sort(tempList1, myComparator);		
			
			ProcessInfo ppp = tempList1.remove(0);
			
			
			
			waitingTime = clock - ppp.getArrivalTime();
			//System.out.println(ppp.getProcessName()+":waitingtime:"+waitingTime);
			totalWaitingTime = totalWaitingTime + waitingTime;
			
			responseTime = clock - ppp.getArrivalTime();
			//System.out.println(ppp.getProcessName()+":resp:"+responseTime);
			totalResponseTime = totalResponseTime + responseTime;
			
			clock = clock + ppp.getTotalExecutionTime();
			
			cpuExecutionTime = cpuExecutionTime + ppp.getTotalExecutionTime();
			
			clock = clock + contextSwitchingTime;
						
			turnAroundTime = clock - ppp.getArrivalTime();
			
			totalTurnAroundTime = totalTurnAroundTime + turnAroundTime;
			//System.out.println(ppp.getProcessName()+":turn around:"+turnAroundTime);
			
			tempList.remove(ppp);

		}
		clock = clock - contextSwitchingTime;
		
		double CPUUtilization = 1 * cpuExecutionTime / clock;
		
		System.out.println("1. Average waiting time:"+totalWaitingTime/myList.size());
		System.out.println("2. Average TurnAround time:"+totalTurnAroundTime/myList.size());
		System.out.println("3. The CPU Utilization::"+CPUUtilization);
		System.out.println("4. Throughtput::"+myList.size()+"/"+clock);
		System.out.println("5. Response Time::"+totalResponseTime/myList.size());
		
		
		

	}

	private LinkedList<ProcessInfo> remainingProceeList(double clock,
			LinkedList<ProcessInfo> tempList) {
		// TODO Auto-generated method stub
		LinkedList<ProcessInfo> myList = new LinkedList<ProcessInfo>();
		double myClock = clock;
		
		for (ProcessInfo p : tempList) {
			if (p.getArrivalTime() <= myClock) {
				myList.add(p);
			}
		}

		return myList;
	}

	//public void display() {
		//for (ProcessInfo P : myList)
			//System.out.println(P.getProcessName() + "-" + P.getArrivalTime()
					//+ "-" + P.getBurstTime() + "-" + P.getPriority() + "-"
					//+ P.getTotalExecutionTime());
	//}

}
