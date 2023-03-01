package database;

import java.util.List;

import com.influxdb.client.write.Point;

import datacarier.Home;
import exception.InvalidException;
import util.NullCheck;

public class UserLayer
{
	InfluxDBDemo demo = null;
	
	private InfluxDBDemo getDemo()
	{
		if(demo == null)
		{
			demo = new InfluxDBDemo();
		}
		return demo;
	}
	
	public void writeWithLineProtocol(String data, String bucket, String org) throws InvalidException
	{
		NullCheck.nullCheck(data);
		NullCheck.nullCheck(bucket);
		NullCheck.nullCheck(org);
		
		InfluxDBDemo demo = getDemo();
		demo.writeWithLineProtocol(data, bucket, org);
	}
	
	public void writeWithDataPoint(Point point, String bucket, String org) throws InvalidException
	{
		NullCheck.nullCheck(point);
		NullCheck.nullCheck(bucket);
		NullCheck.nullCheck(org);
		
		InfluxDBDemo demo = getDemo();
		demo.writeWithDataPoint(point, bucket, org);
	}
	
	public void writeWithPOJO(Home home, String bucket, String org) throws InvalidException
	{
		NullCheck.nullCheck(home);
		NullCheck.nullCheck(bucket);
		NullCheck.nullCheck(org);
		
		InfluxDBDemo demo = getDemo();
		demo.writeWithPOJO(home, bucket, org);
	}
	
	public List<Home> fluxQuery(String bucket, String org) throws InvalidException
	{
		NullCheck.nullCheck(bucket);
		NullCheck.nullCheck(org);
		
		InfluxDBDemo demo = getDemo();
		List<Home> homeList = demo.fluxQuery(bucket, org);
		
		return homeList;
	}
	
	public String createOrganization(String orgName) throws InvalidException
	{
		NullCheck.nullCheck(orgName);
		
		InfluxDBDemo demo = getDemo();
		String orgID = demo.createOrganization(orgName);
		
		return orgID;
	}
	
	public void activateOrganization(String orgName) throws InvalidException
	{
		NullCheck.nullCheck(orgName);
		
		InfluxDBDemo demo = getDemo();
		demo.activateOrganization(orgName);
	}
	
	public void deactivateOrganization(String orgName) throws InvalidException
	{
		NullCheck.nullCheck(orgName);
		
		InfluxDBDemo demo = getDemo();
		demo.deactivateOrganization(orgName);
	}
	
	public void updateOrganization(String orgName, String description) throws InvalidException
	{
		NullCheck.nullCheck(orgName);
		NullCheck.nullCheck(description);
		
		InfluxDBDemo demo = getDemo();
		demo.updateOrganization(orgName, description);
	}
	
	public void createBucket(String orgName, String bucketName, String description, String retentionPolicy) throws InvalidException
	{
		NullCheck.nullCheck(bucketName);
		NullCheck.nullCheck(orgName);
		NullCheck.nullCheck(description);
		
		InfluxDBDemo demo = getDemo();
		demo.createnBucket(orgName, bucketName, description, retentionPolicy);
	}
	
	public void createUser(String userName, String password) throws InvalidException
	{
		NullCheck.nullCheck(userName);
		NullCheck.nullCheck(password);
		
		InfluxDBDemo demo = getDemo();
		demo.createUser(userName, password);
	}
	
}
