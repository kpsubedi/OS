import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.RepaintManager;


public class MultiLevelFeedBack implements SAInterface {

	private LinkedList<ProcessInfo> myList;
	
	private HashMap<String, Double> waitingHash = new HashMap<String, Double>();
	private HashMap<String,Double> turnAroundHash = new HashMap<String, Double>();
	private HashMap<String, Double>  responseHash = new HashMap<String, Double>();
	
	LinkedList<ProcessInfo> q0;// = new LinkedList<ProcessInfo>(myList);
	LinkedList<ProcessInfo> q1;// = new LinkedList<ProcessInfo>();
	LinkedList<ProcessInfo> q2;// = new LinkedList<ProcessInfo>();
	
	
	
	private double clock = 0.0;
	private double cpuExecutionTime = 0.0;
	private double contextSwitchingTime = 2.0;
	
	private double totalWaitingTime = 0.0;
	//private double avgWaitingTime = 0.0;
	private double waitingTime = 0.0;
	
	private double responseTime = 0.0;
	private double totalResponseTime = 0.0;
	
	private double turnAroundTime = 0.0;
	private double totalTurnAroundTime = 0.0;
	
	
	public MultiLevelFeedBack(LinkedList<ProcessInfo> list)
	{
		
		myList = list;
		
		//q0 = new LinkedList<ProcessInfo>();
		//q1 = new LinkedList<ProcessInfo>();
		//q2 = new LinkedList<ProcessInfo>();
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
		
		
		q0 = new LinkedList<ProcessInfo>(myList); 
		q1 = new LinkedList<ProcessInfo>();
		q2 = new LinkedList<ProcessInfo>();
		
		while(q0.size()>0||q1.size()>0||q2.size()>0||tempList.size()>0)
		{
			
			 //this for first queue operation
			//System.out.println(q0.size()+"-"+q1.size()+"-"+q2.size()+"-"+tempList.size());
			//processQ1();
			//processQ2();
			if(tempList.size()>0&&tempList.get(0).getArrivalTime()>=clock)
			{
				
				clock =  tempList.get(0).getArrivalTime();
				q0.add(tempList.remove(0));
			}
			else if(tempList.size()>0&&tempList.get(0).getArrivalTime()<clock)
			{
				q0.add(tempList.remove(0));
			}
			
			
			processQ0();
			
		}
		///display 
		
		//System.out.println(waitingHash.size());
		
		Set<String> myKeys = waitingHash.keySet();
		for(String key:myKeys)
		{
			totalWaitingTime = totalWaitingTime + waitingHash.get(key);
			
			//System.out.println(key+"--->"+waitingHash.get(key));
		}
		
		Set<String> turnKeys = turnAroundHash.keySet();
		for(String keys:turnKeys)
		{
			//System.out.println(keys+"----->"+turnAroundHash.get(keys));
			totalTurnAroundTime = totalTurnAroundTime + turnAroundHash.get(keys);
		}
		
		
		//Set<String> responseKey = responseHash.keySet();
		
		//for(String keyR:responseKey)
		//{
			//System.out.println(keyR+"----->"+responseHash.get(keyR));
		//}
		//System.out.println("The waiting time:"+totalWaitingTime);
		System.out.println("1. The average waiting time:"+totalWaitingTime/myList.size());
		System.out.println("2. Average TurnAround time:"+totalTurnAroundTime/myList.size());
		System.out.println("3. The CPU Utilization::"+1*cpuExecutionTime/clock);
		System.out.println("4. Throughtput::"+myList.size()+"/"+clock);
		System.out.println("5. Response Time::"+totalResponseTime/myList.size());
		
	}


	private void processQ0() {
		// TODO Auto-generated method stub
		double time = 0.0;
		
		double firstQuantum = 8;
		
		//System.out.println("first queue size:"+q0.size());
		//System.out.println("Hello welcome in first queue:");
		//for(ProcessInfo pp:q0)
			//System.out.println(pp.getProcessName());
		
		if(q0.size()==0)
		{
			processQ1();
		//System.out.println("process will go to q1");
		}
		else if (q0.size()>0&&q0.get(0).getArrivalTime()>clock)
		{
			processQ1();
			
		}
		else
		{
			//System.out.println("Hello from first queue:");
			
			ProcessInfo p = q0.remove(0);
			waitingTime = clock - p.getArrivalTime();
			//System.out.println(p.getProcessName()+":wt:"+waitingTime);
			
			//System.out.println(p.getProcessName()+"--->"+p.getTotalExecutionTime());
			
			time = p.getTotalExecutionTime()<firstQuantum?p.getTotalExecutionTime():firstQuantum;
			
			if(p.getCount()==0)
			{
				p.setCount(1);
				
				responseTime = clock - p.getArrivalTime();
				totalResponseTime = totalResponseTime + responseTime;
				
				//System.out.println(p.getProcessName()+":res:"+responseTime);	
				//System.out.println("The total rsponse time:"+totalResponseTime);
				
			}
					
			if(waitingHash.containsKey(p.getProcessName()))
			{
				double value = waitingHash.get(p.getProcessName());
				value = value + waitingTime;
				waitingHash.put(p.getProcessName(), value);
			}
			else
			{
				waitingHash.put(p.getProcessName(), waitingTime);
			}
			//if(this.waitingTable)contains(p) then add this at to existing
			//else insert this wt to the map
			//update the arrival time to clock+time 
			p.setCount(0);
			clock = clock + time + contextSwitchingTime;
			
			cpuExecutionTime = cpuExecutionTime + time;
			
			//System.out.println(p.getProcessName()+":"+cpuExecutionTime);
			//System.out.println(p.getProcessName()+":"+clock);
			
			p.setTotalExecutionTime(p.getTotalExecutionTime()-time);
			
						
			if(p.getTotalExecutionTime()>0)
			{
				q1.add(p);
			}
			else
			{
				turnAroundTime = clock - p.getArrivalTime();
				//System.out.println(p.getProcessName()+":turn around time:"+turnAroundTime);
				
				if(turnAroundHash.containsKey(p.getProcessName()))
				{
					double value = turnAroundHash.get(p.getProcessName());
					value = value + turnAroundTime;
					turnAroundHash.put(p.getProcessName(), value);
				}
				else
				{
					turnAroundHash.put(p.getProcessName(), turnAroundTime);
					
				}
			}
			
		}
			
	}
	
	
	private void processQ1() {
		// TODO Auto-generated method stub
		double time = 0.0;
		double secondQuantum = 16.0;
		
		if(q1.size()==0)
			processQ2();
		else if(q1.size()>0&&q1.get(0).getArrivalTime()>clock)
			processQ2();
		else
		{
			ProcessInfo p = q1.remove(0);
			
			//System.out.println(p.getProcessName()+"--->"+p.getTotalExecutionTime());
			time = p.getTotalExecutionTime()<secondQuantum?p.getTotalExecutionTime():secondQuantum;
				
			
			waitingTime = clock - p.getArrivalTime();
			
			if(waitingHash.containsKey(p.getProcessName()))
			{
				double value = waitingHash.get(p.getProcessName());
				value = value + waitingTime;
				waitingHash.put(p.getProcessName(), value);
				
			}
			else
			{
				waitingHash.put(p.getProcessName(), waitingTime);
			}
			
			clock = clock + time + contextSwitchingTime;
			cpuExecutionTime = cpuExecutionTime + time;
			
			p.setTotalExecutionTime(p.getTotalExecutionTime()-time);
			
			if(p.getTotalExecutionTime()>0)
			{
				q2.add(p);
			}
			else
			{
				turnAroundTime = clock - p.getArrivalTime();
				//System.out.println(p.getProcessName()+":turn around time:"+turnAroundTime);
				
				if(turnAroundHash.containsKey(p.getProcessName()))
				{
					double value = turnAroundHash.get(p.getProcessName());
					value = value + turnAroundTime;
					turnAroundHash.put(p.getProcessName(), value);
				}
				else
				{
					turnAroundHash.put(p.getProcessName(), turnAroundTime);
					
				}
				
			}
		}
		
		
		
	}


	private void processQ2() {
		// TODO Auto-generated method stub
		//double time = 0.0;
		
		if(q2.size()>0)
		{
			ProcessInfo p = q2.remove(0);
			//System.out.println(p.getProcessName()+"--->"+p.getTotalExecutionTime());
			
			waitingTime = clock - p.getArrivalTime();
			//for waiting time
			if(waitingHash.containsKey(p.getProcessName()))
			{
				double value = waitingHash.get(p.getProcessName());
				value = value + waitingTime;
				waitingHash.put(p.getProcessName(), value);
			}
			else
			{
				waitingHash.put(p.getProcessName(), waitingTime);
			}
			//waiting time
			
			clock = clock + q2.get(0).getTotalExecutionTime() + contextSwitchingTime;
			turnAroundTime = clock - p.getArrivalTime();
			//System.out.println(p.getProcessName()+":turn around time:"+turnAroundTime);
			
			if(turnAroundHash.containsKey(p.getProcessName()))
			{
				double value = turnAroundHash.get(p.getProcessName());
				value = value + turnAroundTime;
				turnAroundHash.put(p.getProcessName(), value);
			}
			else
			{
				turnAroundHash.put(p.getProcessName(), turnAroundTime);
				
			}
			
		}
		
	}
	
	public void displayFromMulti()
	{
		for(ProcessInfo p: q0)
			System.out.println(p.getProcessName()+"-"+p.getArrivalTime()+"-"+p.getBurstTime()+"-"+p.getPriority()+"-"+p.getTotalExecutionTime());
		
	}
	
	/*public void displayWaitingTime()
	{
		
		System.out.println(waitingHash.size());
		Set<String> myKeys = waitingHash.keySet();
		for(String key:myKeys)
			System.out.println(key+"--->"+waitingHash.get(key));
		
	}*/

}
