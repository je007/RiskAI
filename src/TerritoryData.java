import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TerritoryData {
	public static List<Territory> initTerritoryData()
	{
		List<Territory> territoryList = new ArrayList<Territory>();
		
		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader("TerritoryData.txt"));
		}
		catch(FileNotFoundException e)
		{
			throw new RuntimeException("File Not Found");
		}
		String line=null;
		try {
			while((line= reader.readLine())!= null)
			{
				//DO FILE STUFF HERE
				territoryList.add(initTerritory(line));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return territoryList;
	}
	private static Territory initTerritory(String territoryData, List<Continent> continentList)
	{
		char currentChar;
		int currentDataIndex=0;
		String name="";
		String continent="";
		int numberOfAjacentTerritories=0;
		String tempAjacentName="";
		List<String> ajacentTerritoryNames = new ArrayList<String>();
		
		
		for(int i=0;i<territoryData.length();i++)
		{
			if((currentChar=territoryData.charAt(i))!=';')
			{
				if(currentDataIndex==0)name+=currentChar;
				if(currentDataIndex==1)continent+=currentChar;
				if(currentDataIndex==2)numberOfAjacentTerritories+=(int)currentChar;
				if(currentDataIndex==3)
				{
					if(currentChar!=',')
					{
						tempAjacentName+=currentChar;
					}
					else
					{
						ajacentTerritoryNames.add(tempAjacentName);
						tempAjacentName=new String("");
					}
				}				
			}
			else
			{
				currentDataIndex=++currentDataIndex%4;
			}
			
		}
		return new Territory(name, ContinentData.findContinentByName(continent,continentList), ajacentTerritoryNames);
	}
	public static Territory findTerritoryByName(String territoryName, List<Territory> territoryList)
	{
		for (Territory i : territoryList)
		{
			if(i.name==territoryName) return i;
		}
		return new Territory();
		}
	}
	
	public void linkTerritories(List<Territory> territoryList)
	{
		for (Territory territory : territoryList)
		{
			for (String name : territory.getAjacentTerritoryNameList())
			{
				territory.link(TerritoryData.findTerritoryByName(name,territoryList));
			}			
		}
	}
	

}