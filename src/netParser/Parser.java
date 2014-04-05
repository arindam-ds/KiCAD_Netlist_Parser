package netParser;

import java.io.*;
import java.util.*;

public class Parser {

	/**
	 * @param args
	 */
	static int DEBUG=1;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int numberOfComponents=0, start, end, numOfPins=0, netId=0, pin=0;
		List<Components> compList = new ArrayList<Components>();
		List<Nets> netList = new ArrayList<Nets>();
		String strLine, compName = null, partName = null, libPartName = null, netCompName = null;
		
		
		//Read file
		if(args.length != 1) 
		{
			  System.err.println("Invalid command line, exactly one argument required to specify input file");
			  System.exit(1);
		}
		
		try
		{
			FileInputStream fstream = new FileInputStream(args[0]);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			

			while ((strLine = br.readLine()) != null)   {
				/*if(DEBUG==1) {
					System.out.println (strLine);  //DEBUG info
				}*/
				
				if(strLine.contains("comp ")){
					numberOfComponents++;
					
					start = strLine.indexOf("ref ") + 4;
					end = start;
					
					for (final char c : strLine.substring(start).toCharArray())
					{
	            		if (c == ')') 
	            		{
	            			break;
	            		}
	            		++end;
					}
					//System.out.println(strLine.substring(start, end));
					compName = strLine.substring(start, end);
	        
					for(int i=0;i<2;i++)
						strLine = br.readLine();
					
					if(strLine.contains("part "))
					{
						start = strLine.indexOf("part ") + 5;
						end = start;
						for (final char c : strLine.substring(start).toCharArray())
							{
			            		if (c == ')') 
			            		{
			            			break;
			            		}
			            		++end;
							}
			        //System.out.println(strLine.substring(start, end));
					partName = strLine.substring(start, end);
					compList.add(new Components(compName,partName,0));
					
					}//if contains part
				}	//if contains comp		
				
				if(strLine.contains("libpart "))
				{
					start = strLine.indexOf("(part ") + 6;
					end = start;
					
					for (final char c : strLine.substring(start).toCharArray())
					{
	            		if (c == ')') 
	            		{
	            			break;
	            		}
	            		++end;
					}
					//System.out.println(strLine.substring(start, end));
					libPartName = strLine.substring(start, end);		
					numOfPins=0;
				}
				if(strLine.contains("pin ("))
				{
					numOfPins++;
				}
				
				
				for(int j=0;j<compList.size();j++)
				{
					if(compList.get(j).nameOfCompPart.equalsIgnoreCase(libPartName) && numOfPins>0)
					{
						compList.get(j).numOfPin = numOfPins;
					}												
				}
				
				if(strLine.contains("(net "))
				{
					netId++;
				}
				
				if(strLine.contains("(node "))
				{
					start = strLine.indexOf("(ref ") + 5;
					end = start;
					
					for (final char c : strLine.substring(start).toCharArray())
					{
	            		if (c == ')') 
	            		{
	            			break;
	            		}
	            		++end;
					}
					//System.out.println(strLine.substring(start, end));
					netCompName = strLine.substring(start, end);
					
					start = strLine.indexOf("(pin ") + 5;
					end = start;
					
					for (final char c : strLine.substring(start).toCharArray())
					{
	            		if (c == ')') 
	            		{
	            			break;
	            		}
	            		++end;
					}
					
					pin = Integer.parseInt(strLine.substring(start, end));
					
					netList.add(new Nets(netId, netCompName, pin));
				}
				
			}//end of while ((strLine = br.readLine()) != null) 

			//Close the input stream
			br.close();
			
			
			if(DEBUG==1) {
				System.out.println (numberOfComponents);
				for(int j=0;j<compList.size();j++)
					System.out.println(compList.get(j).nameOfComp+" "+compList.get(j).nameOfCompPart+" "+compList.get(j).numOfPin);
			
				for(int j=0;j<netList.size();j++)
					System.out.println(netList.get(j).netId+" "+netList.get(j).compName+" "+netList.get(j).pin);
			}
				
		}
		
		catch (Exception e)
		{
            System.err.println("Error: " + e.getMessage());
        }
		

	}

}
