/**
 * @classname: FlightPredictionMapper
 * 
 * @author Ruinan Hu
 *
 */


import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Calendar;

public class FlightPredictionMapper
	extends Mapper<LongWritable, Text, Text, IntWritable> {
@Override
	public void map(LongWritable Key, Text Value, Context context)
		throws IOException, InterruptedException {

		FlightPriceParser FParser = new FlightPriceParser();
		if (!FParser.map(Value.toString())){
			return;
		}
		context.write(new Text(FParser.Carrier), new IntWritable(FParser.DayOfMonth));
	}
}
