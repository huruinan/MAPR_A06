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

import org.rosuda.JRI.Rengine;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RList;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.RMainLoopCallbacks;

class TextConsole implements RMainLoopCallbacks
{
    public void rWriteConsole(Rengine re, String text, int oType) {
        System.out.print(text);
    }
    
    public void rBusy(Rengine re, int which) {
        System.out.println("rBusy("+which+")");
    }
    
    public String rReadConsole(Rengine re, String prompt, int addToHistory) {
        System.out.print(prompt);
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            String s=br.readLine();
            return (s==null||s.length()==0)?s:s+"\n";
        } catch (Exception e) {
            System.out.println("jriReadConsole exception: "+e.getMessage());
        }
        return null;
    }
    
    public void rShowMessage(Rengine re, String message) {
        System.out.println("rShowMessage \""+message+"\"");
    }
	
    public String rChooseFile(Rengine re, int newFile) {
	return "test";
    }
    
    public void   rFlushConsole (Rengine re) {
    }
	
    public void   rLoadHistory  (Rengine re, String filename) {
    }			
    
    public void   rSaveHistory  (Rengine re, String filename) {
    }			
}
public class FlightPredictionReducer
	extends Reducer<Text, IntWritable, Text, Text> {
	public boolean DEV_MODE = false;
@Override
	public void reduce(Text Key, Iterable<IntWritable> Values, Context context)
	throws IOException, InterruptedException {
		String[] args= {"test"};
		REXP x;
		if (!Rengine.versionCheck()) {
			System.out.println("** Version mismatch - Java files don't match library version.");
		}
		Rengine re=new Rengine(args, false, new TextConsole());
		context.write(Key, new Text(System.getenv("R_HOME")));
		//if (!re.waitForR()) {
		//	context.write(Key, new Text("Bad"));
		//	return;
	       	//}
		boolean by[] = { true, false, false };
		//re.assign("bool", by);
		//re.eval("system(\"ls ~/ > ~/tt\")",false);
		//x=re.eval("bool");
		//String sr = x.toString();
	}
}
