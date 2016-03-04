/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @auth|| Ruinan
 */



import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.GZIPInputStream;
import com.opencsv.CSVReader;
import com.opencsv.CSVParser;


public class FlightPriceParser{
	public boolean DEV_MODE = true;
	public int CRSArrTime=0;
	public int CRSDepTime=0;
	public int CRSElapsedTime=0;
	public int timeZone=0;
	public int AirportIDO=0;
	public int AirportIDD=0;
	public int AirportSeqIDO=0;
	public int AirportSeqIDD=0;
	public int CityMarketIDO=0;
	public int CityMarketIDD=0;
	public int StateFipsO=0;
	public int StateFipsD=0;
	public int WacO=0;
	public int WacD=0;
	public int Cancelled=0;
	public int ArrTime=0;
	public int DepTime=0;
	public int ActualElapsedTime=0;
	public int Year=0;
	public int Month=0;
	public int DayOfMonth = 0;
	public double ArrDel15=0;
	public double ArrDelay=0;
	public double ArrDelayMinutes=0;
	public double Price;
	public String Carrier;
	public String Origin;
	public String Destination;
	public CSVParser Parser;

	FlightPriceParser(){

	}
	public boolean map(String nline){

		CSVParser Parser = new CSVParser();
		String[] readline;		
		try{
			readline = Parser.parseLine(nline);
		}catch(Exception e){
			if (DEV_MODE)			
				System.out.println("Can't parse the nline");
			return false;			
		} 

		try{
			CRSArrTime = Integer.parseInt(readline[40]); 
		}catch(NumberFormatException e)
		{
			if (DEV_MODE)			
				System.out.println("CRSArrTime:"+readline[40]);
			return false;
		}
		
		try{
			CRSDepTime = Integer.parseInt(readline[29]);
		}catch(NumberFormatException e) 
		{
			if (DEV_MODE)			
				System.out.println("CRSDepTime:"+readline[29]);
			return false;
		}

		try{
			CRSElapsedTime = Integer.parseInt(readline[50]);
		}catch(NumberFormatException e) 
		{
			if (DEV_MODE)			
				System.out.println("CRSElapsedTime:"+readline[50]);
			return false;
		}

		try{
			AirportIDO = Integer.parseInt(readline[11]);
		}catch(NumberFormatException e) 
		{
			if (DEV_MODE)			
				System.out.println("AirportIDO:"+readline[11]);
			return false;
		}

		try{
			AirportIDD = Integer.parseInt(readline[20]);
		}catch(NumberFormatException e) 
		{
			if (DEV_MODE)			
				System.out.println("AirportIDD:"+readline[20]);
			return false;
		}

		try{
			AirportSeqIDO = Integer.parseInt(readline[12]);
		}catch(NumberFormatException e) 
		{
			if (DEV_MODE)			
				System.out.println("AirportSeqIDO:"+readline[12]);
			return false;
		}

		try{
			AirportSeqIDD = Integer.parseInt(readline[21]);
		}catch(NumberFormatException e) 
		{
			if (DEV_MODE)			
				System.out.println("AirportSeqIDD:"+readline[21]);
			return false;
		}

		try{
			CityMarketIDO = Integer.parseInt(readline[13]);
		}catch(NumberFormatException e)
		{
			if (DEV_MODE)
				System.out.println("AirportSeqIDD:"+readline[13]);
			return false;
		}
		
		try{
			CityMarketIDD = Integer.parseInt(readline[22]);
		}catch(NumberFormatException e)
		{
			if (DEV_MODE)
				System.out.println("CityMarketIDD:"+readline[22]);
			return false;
		}

		try{
			StateFipsO = Integer.parseInt(readline[17]);
		}catch(NumberFormatException e)
		{
			if (DEV_MODE)
				System.out.println("StateFipsO:"+readline[17]);
			return false;
		}

		try{
			StateFipsD = Integer.parseInt(readline[26]);
		}catch(NumberFormatException e)
		{
			if (DEV_MODE)
				System.out.println("StateFipsD:"+readline[26]);
			return false;
		}

		try{
			WacO = Integer.parseInt(readline[19]);
		}catch(NumberFormatException e)
		{
			if (DEV_MODE)
				System.out.println("WacO:"+readline[19]);
			return false;
		}

		try{
			WacD = Integer.parseInt(readline[28]);
		}catch(NumberFormatException e)
		{
			if (DEV_MODE)
				System.out.println("WacD:"+readline[28]);
			return false;
		}

		try{
			Cancelled = Integer.parseInt(readline[47]);
		}catch(NumberFormatException e)
		{
			if (DEV_MODE)
				System.out.println("Cancelled:"+readline[47]);
			return false;
		}

		try{
			ArrTime = Integer.parseInt(readline[41]);
		}catch(NumberFormatException e)
		{
			if (DEV_MODE)
				System.out.println("ArrTime:"+readline[41]);
			return false;
		}

		try{
			DepTime = Integer.parseInt(readline[30]);
		}catch(NumberFormatException e)
		{
			if (DEV_MODE)
				System.out.println("DepTime:"+readline[30]);
			return false;
		}
                
		try{
                        ActualElapsedTime = Integer.parseInt(readline[51]);
                }catch(NumberFormatException e)
                {
			if (DEV_MODE)
                        	System.out.println("ActualElapsedTime:"+readline[51]);
                        return false;
                }
		//Send out error but not do the sanity check
		try{
                        Year = Integer.parseInt(readline[0]);
                }catch(NumberFormatException e)
                {
			if (DEV_MODE)
                        	System.out.println("Year:"+readline[0]);
                        return false;
                }
		//Send out error but not do the sanity check
		try{
                        Month = Integer.parseInt(readline[2]);
                }catch(NumberFormatException e)
                {
			if (DEV_MODE)
                        	System.out.println("Month:"+readline[2]);
                        return false;
                }

		try{
			DayOfMonth = Integer.parseInt(readline[3]);
		}catch(NumberFormatException e)
                {
			if (DEV_MODE)
                        	System.out.println("DayOfMonth:"+readline[3]);
                        return false;
                }

		try{
			ArrDelay = Double.parseDouble(readline[42]);
		}catch(NumberFormatException e)
		{
			if (DEV_MODE)
				System.out.println("ArrDelay:"+readline[42]);
			return false;
		}

		try{
                        ArrDel15 = Double.parseDouble(readline[44]);
                }catch(NumberFormatException e)
                {
			if (DEV_MODE)
                       		System.out.println("ArrDel15:"+readline[44]);
                        return false;
                }

		try{
			ArrDelayMinutes = Double.parseDouble(readline[43]);
		}catch(NumberFormatException e)
		{
			if (DEV_MODE)
				System.out.println("ArrDelayMinutes:"+readline[43]);
			return false;
		}
		try{
			Price = Double.parseDouble(readline[109]);
		}catch(NumberFormatException e)
		{
			if (DEV_MODE)
				System.out.println("Price:"+readline[109]);
			return false;
		}

					
		/*
		System.out.println("CRSArrTime:"+CRSArrTime);
		System.out.println("CRSDepTime:"+CRSDepTime);
		System.out.println("CRSElapsedTime:"+CRSElapsedTime);
		System.out.println("AirportIDO:"+AirportIDO);
		System.out.println("AirportIDD:"+AirportIDD);
		System.out.println("AirportSeqIDO:"+AirportSeqIDO);
		System.out.println("AirportSeqIDD:"+AirportSeqIDD);
		System.out.println("CityMarketIDO:"+CityMarketIDO);
		System.out.println("CityMarketIDD:"+CityMarketIDD);
		System.out.println("StateFipsO:"+StateFipsO);
		System.out.println("StateFipsD:"+StateFipsD);
		System.out.println("WacO:"+WacO);
		System.out.println("WacD:"+WacD);
		System.out.println("Cancelled:"+Cancelled);
		System.out.println("ArrTime:"+ArrTime);
		System.out.println("DepTime:"+DepTime);
		System.out.println("ArrDelay:"+ArrDelay);
		System.out.println("ArrDelayMinutes:"+ArrDelayMinutes);
		*/

		//CRSArrTime[40] and CRSDepTime[29] should not be zero
		if (CRSArrTime == 0 || CRSDepTime == 0) {
			if (DEV_MODE)
				System.out.println("CRS");
			return false;
		}

		int CRSArrMin = 0;
		int CRSDepMin = 0;
		int CRSArrMinO = 0;
		CRSArrMin = CRSArrTime / 100 * 60 + CRSArrTime%100;
		CRSDepMin = CRSDepTime / 100 * 60 + CRSDepTime%100;
		CRSArrMinO = CRSDepMin + CRSElapsedTime;
		timeZone = CRSArrMin - CRSArrMinO;
		//System.out.println("CRSArrMinO:"+CRSArrMinO);
		//System.out.println("CRSDepMin:"+CRSDepMin);
		//System.out.println("timeZone:"+timeZone);
		//timeZone % 60 should be 0
		if (timeZone%60 != 0) { 
			if (DEV_MODE)
				System.out.println("timeZone");
			return false;
		}

		//AirportID[11][20],  AirportSeqID[12][21], CityMarketID[13][22], StateFips[17][26], Wac[19][28] should be larger than 0

		if ( AirportIDO <= 0 ||
			AirportIDD <= 0 ||
			AirportSeqIDO <= 0 ||
			AirportSeqIDD <= 0 ||
			CityMarketIDO <= 0 ||
			CityMarketIDD <= 0 ||
			StateFipsO <= 0 ||
			StateFipsD <= 0 ||
			WacO <= 0 ||
			WacO <= 0 ) {
			if (DEV_MODE) 
				System.out.println("a lot of names");
			return false;
		}


		//Origin[14], Destination[23],  CityName[15][24], State[16][25], StateName[18][27] should not be empty
		Origin = readline[14];
		Destination = readline[23];
		if (readline[14].isEmpty() || 
			readline[23].isEmpty() ||
			readline[15].isEmpty() ||
			readline[24].isEmpty() ||
			readline[16].isEmpty() ||
			readline[25].isEmpty() ||
			readline[18].isEmpty() ||
			readline[27].isEmpty()) {
			if (DEV_MODE)
				System.out.println("Empty Strings");
			return false;
		}

		//F|| flights that not Cancelled[47]:
		if (Cancelled == 1) {
			if (DEV_MODE)
				System.out.println("Cancelled");	
			return false;
		}

		int ArrMin = 0;
		int DepMin = 0;
		int FlightTime = 0;
		ArrMin = ArrTime / 100 *60 + ArrTime % 100;
		DepMin = DepTime / 100 *60 + DepTime % 100;
		//ArrTime[41] -  DepTime[30] - ActualElapsedTime[51] - timeZone should be zero
		FlightTime = ArrMin - DepMin -  ActualElapsedTime  -timeZone;
		
		//System.out.println("FlightTime:"+FlightTime);
		if (FlightTime%1440 !=0) {
			if (DEV_MODE)
				System.out.println("FlightTime:"+FlightTime);
			return false;
		}

		//if ArrDelay[42] > 0 then ArrDelay should equal to ArrDelayMinutes[43]
		if (ArrDelay > 0 && ArrDelayMinutes != ArrDelay ) { 
			if (DEV_MODE)
				System.out.println("ArrDelay > 0");	
			return false;
		}
		
		//if ArrDelayMinutes >= 15 then ArrDel15 should be true
		if (ArrDelayMinutes >= 15 && ArrDel15 != 1){
			if (DEV_MODE)
				System.out.println("ArrDelay > 0");
			return false;
		}

		if ( Price < 0 ){
			if (DEV_MODE)
				System.out.println("Price less than 0");
			return true;
		}
		//[8]
		Carrier = readline[8];
		if (Carrier.isEmpty()){
			if (DEV_MODE)
				System.out.println("Carrier Strings Empty");
			return true;
		}
		
		return true;
	}

}
