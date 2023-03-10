package telran.rentcompanyserver.dto;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class StatisticsData implements Serializable
{
	LocalDate fromDate;
	LocalDate toDate;
	int fromAge;
	int toAge;
	
	public StatisticsData()
	{
		// TODO Auto-generated constructor stub
	}

	public StatisticsData(LocalDate fromDate, LocalDate toDate, int fromAge, int toAge)
	{
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.fromAge = fromAge;
		this.toAge = toAge;
	}

	public LocalDate getFromDate()
	{
		return fromDate;
	}

	public LocalDate getToDate()
	{
		return toDate;
	}

	public int getFromAge()
	{
		return fromAge;
	}

	public int getToAge()
	{
		return toAge;
	}
	
	
}
