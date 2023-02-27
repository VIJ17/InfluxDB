package datacarier;

import java.time.Instant;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

@Measurement(name = "home")
public class Home
{
	@Column(tag = true)
	private String room;
	@Column
	private double temp;
	@Column
	private double hum;
	@Column
	private long co;
	@Column(timestamp = true)
	Instant time;
	
	public String getRoom()
	{
		return room;
	}
	public void setRoom(String room)
	{
		this.room = room;
	}
	public double getTemp()
	{
		return temp;
	}
	public void setTemp(double temp)
	{
		this.temp = temp;
	}
	public double getHum()
	{
		return hum;
	}
	public void setHum(double hum)
	{
		this.hum = hum;
	}
	public long getCo()
	{
		return co;
	}
	public void setCo(long co)
	{
		this.co = co;
	}
	public Instant getTime()
	{
		return time;
	}
	public void setTime(Instant time)
	{
		this.time = time;
	}
}
