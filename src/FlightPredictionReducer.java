/**
 * @classname: FlightPriceReducer
 * 
 * @author Ruinan Hu
 *
 */

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.*;
import java.io.*;
import java.lang.Math;

import java.util.Enumeration;

import org.rosuda.REngine.Rserve.*;
import org.rosuda.REngine.*;

public class FlightPredictionReducer
	extends Reducer<Text, IntWritable, Text, Text> {
	public boolean DEV_MODE = false;
@Override
	public void reduce(Text Key, Iterable<IntWritable> Values, Context context)
	throws IOException, InterruptedException {
		
		double d[]={};
		REXP x;
		try {
        		RConnection c = new RConnection("127.0.0.1", 1035);
			d = c.eval("rnorm(10)").asDoubles();
		} catch (Exception e) {
			System.out.println("EX:"+e);
			e.printStackTrace();
		}		
		
		context.write(Key, new Text(Double.toString(d[0])));
	}
}
