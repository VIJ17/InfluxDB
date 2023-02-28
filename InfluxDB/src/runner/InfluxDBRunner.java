package runner;

import java.time.Instant;
import java.util.List;
import java.util.Scanner;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

import database.UserLayer;
import datacarier.Home;
import exception.InvalidException;

public class InfluxDBRunner
{

	Scanner sc = new Scanner(System.in);
	
	private int getInt()
	{
		int value = sc.nextInt();
		sc.nextLine();
		return value;
	}
	
	private String getString()
	{
		String str = sc.nextLine();
		return str;
	}
	
	private String getBucket()
	{
		System.out.println("Enter the bucket name...");
		String bucket = getString();
		
		return bucket;
	}
	
	private String getOrganization()
	{
		System.out.println("Enter the organization name...");
		String org = getString();
		
		return org;
	}
	
	public static void main(String[] args)
	{
		InfluxDBRunner runner = new InfluxDBRunner();
		UserLayer user = new UserLayer();
		
		System.out.println("Enter the case value to execute...");
		int caseValue = runner.getInt();
		
		switch(caseValue)
		{
			case 1:					//InfluxDB line protocol to write data...
			{
				System.out.println("Enter the no of data points to write...");
				int n = runner.getInt();
				
				for(int i = 0; i < n; i++)
				{
					System.out.println("Enter data point...");
					String data = runner.getString();
					try
					{
						user.writeWithLineProtocol(data, runner.getBucket(), runner.getOrganization());
					}
					catch (InvalidException e)
					{
						e.printStackTrace();
					}
				}
				
				break;
			}
			case 2:					//Data Point to write data...
			{
				Point point = Point.measurement("home").addTag("room", "Living Room").addField("temp", 29.9).addField("hum", 33.3).addField("co", 3).time(Instant.now(), WritePrecision.S);
				
				try
				{
					user.writeWithDataPoint(point, runner.getBucket(), runner.getOrganization());
				}
				catch (InvalidException e)
				{
					e.printStackTrace();
				}
				
				break;
			}
			case 3:					//POJO to write data...
			{
				Home home = new Home();
				home.setRoom("Kitchen");
				home.setTemp(31.3);
				home.setHum(21d);
				home.setCo(18);
				home.setTime(Instant.now());
				
				try
				{
					user.writeWithPOJO(home, runner.getBucket(), runner.getOrganization());
				}
				catch(InvalidException e)
				{
					e.printStackTrace();
				}
				
				break;
			}
			case 4:					//Flux Query...
			{
				try
				{
					List<Home> homeList = user.fluxQuery(runner.getBucket(), runner.getOrganization());
					
					for(Home home : homeList)
					{
						System.out.println("ROOM : " + home.getRoom());
						System.out.println("TIME : " + home.getTime());
						System.out.println("TEMPERATURE : " + home.getTemp());
						System.out.println("HUMIDITY : " + home.getHum());
						System.out.println("CARBON MONOXIDE : " + home.getCo());
					}
				}
				catch(InvalidException e)
				{
					e.printStackTrace();
				}
				
				break;
			}
			
			case 5:					//Create Organization...
			{
				try
				{
					user.createOrganization(runner.getOrganization());
				}
				catch(InvalidException e)
				{
					e.printStackTrace();
				}
				
				break;
			}
			case 6:					//Update Organization...
			{
				System.out.println("Enter the Description to add for the organization...");
				String description = runner.getString();
				
				try
				{
					user.updateOrganization(runner.getOrganization(), description);
				}
				catch(InvalidException e)
				{
					e.printStackTrace();
				}
				
				break;
			}
			
			case 7:					//Activate Organization...
			{
				try
				{
					user.activateOrganization(runner.getOrganization());
				}
				catch(InvalidException e)
				{
					e.printStackTrace();
				}
				
				break;
			}
			case 8:					//Deactivate Organization...
			{
				try
				{
					user.deactivateOrganization(runner.getOrganization());
				}
				catch(InvalidException e)
				{
					e.printStackTrace();
				}
				
				break;
			}
			case 9:					//Create Bucket...
			{
				System.out.println("Enter the Description to add for the bucket...");
				String description = runner.getString();
				System.out.println("Enter the Retention Policy...");
				String retentionPolicy = runner.getString();
				
				try
				{
					user.createBucket(runner.getOrganization(), runner.getBucket(), description, retentionPolicy);
				}
				catch(InvalidException e)
				{
					e.printStackTrace();
				}
				
				break;
			}
		}
		
	}

}
