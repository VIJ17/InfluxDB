package database;

import java.util.ArrayList;
import java.util.List;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import datacarier.Home;

public class InfluxDBDemo
{
	
	private void closeConnection(InfluxDBClient client)
	{
		try
		{
			client.close();
		}
		catch(Exception e) {}
	}
	
	private InfluxDBClient createConnection()
	{
//		String token = "VF7mD4pde6wuso8L7iQkPJdcZITuWMWjyl8JzjaFUuN_HYGUPYBXKxLKaVUeER0M2xZGU-RmfktsLUj0o_aYNg==";
//		InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());
		
		InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", "vijay", "Root@123".toCharArray());
		return client;
	}
	
	//InfluxDB line protocol to write data...
	public void writeWithLineProtocol(String data, String bucket, String org)
	{
		InfluxDBClient client = null;
		
		try
		{
			client = createConnection();
			WriteApiBlocking writeApi = client.getWriteApiBlocking();
			
			writeApi.writeRecord(bucket, org, WritePrecision.S, data);
		}
		finally
		{
			if(client != null)
			{
				closeConnection(client);
			}
		}
	}
	
	//Data Point to write data...
	public void writeWithDataPoint(Point point, String bucket, String org)
	{
		InfluxDBClient client = null;
		
		try
		{
			client = createConnection();
			WriteApiBlocking writeApi = client.getWriteApiBlocking();
			
			writeApi.writePoint(bucket, org, point);
		}
		finally
		{
			if(client != null)
			{
				closeConnection(client);
			}
		}
	}
	
	//POJO to write data...
	public void writeWithPOJO(Home home, String bucket, String org)
	{
		InfluxDBClient client = null;
		
		try
		{
//			client = createConnection();
//			WriteApiBlocking writeApi = client.getWriteApiBlocking();
//			
//			writeApi.writeRecord(bucket, org, WritePrecision.S, home);
		}
		finally
		{
			if(client != null)
			{
				closeConnection(client);
			}
		}
	}
	
	//Flux Query...
	public List<Home> fluxQuery(String bucket, String org)
	{
		String query = "from(bucket: \"DEMO\")\n"
				+ "    |> range(start: 2022-01-01T14:00:00Z, stop: 2022-01-01T20:00:01Z)\n"
				+ "    |> filter(fn: (r) => r._measurement == \"home\")\n"
				+ "    |> filter(fn: (r) => r._field == \"co\" or r._field == \"hum\" or r._field == \"temp\")\n"
				+ "    |> filter(fn: (r) => r.room == \"Kitchen\")\n"
				+ "    |> pivot(rowKey: [\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")";
		
		InfluxDBClient client = null;
		
		try
		{
			client = createConnection();
			QueryApi queryApi = client.getQueryApi();
			List<FluxTable> tables = queryApi.query(query, org);
			List<Home> homeList = getTableValues(tables);
			
			return homeList;
		}
		finally
		{
			if(client != null)
			{
				closeConnection(client);
			}
		}
	}
	
	private List<Home> getTableValues(List<FluxTable> tables)
	{
		List<Home> homeList = null;
		
		for(FluxTable table : tables)
		{
			homeList = new ArrayList<>();
			
			for(FluxRecord record : table.getRecords())
			{
				Home home = new Home();
				
				home.setRoom(record.getValueByKey("room").toString());
				home.setTime(record.getTime());
				home.setTemp((double) record.getValueByKey("temp"));
				home.setHum((double) record.getValueByKey("hum"));
				home.setCo((long) record.getValueByKey("co"));
				
				homeList.add(home);
			}
		}
		return homeList;
	}
	
}
