/**
 * @classname: FlightPrice
 * 
 * @author White,Tom 
 * @maintainer Hu,Ruinan 	
 *
 */

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class FlightPrediction {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Wrong Syntax: FlightPrediction <input path> <input path>");
			System.exit(-1);
		}
		Job job = new Job();
		job.setJarByClass(FlightPrediction.class);
		job.setJobName("Flight Prediction");
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setMapperClass(FlightPredictionMapper.class);
		job.setReducerClass(FlightPredictionReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
