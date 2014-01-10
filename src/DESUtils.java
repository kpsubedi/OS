import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class DESUtils {
	
	//private LinkedList<ProcessInfo> myLinkedList = new LinkedList<ProcessInfo>();
	private Hashtable <String, LinkedList> processGroupList = new Hashtable <String, LinkedList>();
	
	public Hashtable readProcessInfo(String fileName)
	{
		LinkedList<ProcessInfo> myLinkedList = null;
		String processGroupName = null;
		try {
			Scanner sc = new Scanner(new File(fileName));
			
			while(sc.hasNext())
			{
				String line = sc.nextLine();
				if (line.trim().isEmpty()) continue;
				if (line.startsWith("#"))
				{
					if (myLinkedList != null)
					{
						processGroupList.put(processGroupName, myLinkedList);
						myLinkedList = null;
					}
					myLinkedList = new LinkedList<ProcessInfo>();
					processGroupName = line.trim();
					continue;
				}
				String[] tokens = line.split("\\s");
				ProcessInfo pi = new ProcessInfo();
				pi.setProcessName(tokens[0]);
				pi.setArrivalTime(Double.parseDouble(tokens[1]));
				pi.setBurstTime(Double.parseDouble(tokens[2]));
				pi.setPriority(Double.parseDouble(tokens[3]));
				pi.setTotalExecutionTime(Double.parseDouble(tokens[4]));
				
				myLinkedList.add(pi);
				
			}
			
			if (myLinkedList != null)
			{
				processGroupList.put(processGroupName, myLinkedList);
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return processGroupList;
		
	}

}
