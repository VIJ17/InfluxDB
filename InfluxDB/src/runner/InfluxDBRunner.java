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
	
	public static void main(String[] args)
	{
		InfluxDBRunner runner = new InfluxDBRunner();
		UserLayer user = new UserLayer();
		
		System.out.println("Enter the bucket name...");
		String bucket = runner.getString();
		System.out.println("Enter the organization name...");
		String org = runner.getString();
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
						user.writeWithLineProtocol(data, bucket, org);
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
					user.writeWithDataPoint(point, bucket, org);
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
					user.writeWithPOJO(home, bucket, org);
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
					List<Home> homeList = user.fluxQuery(bucket, org);
					
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
		}
		
	}

}
