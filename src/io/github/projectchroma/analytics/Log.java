package io.github.projectchroma.analytics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class Log{
	public static final File LOG_DIR = new File("logs");
	public static final int DEBUG = 0, INFO = 1, WARNING = 2, ERROR = 3, FATAL_ERROR = 4;
	private static final String[] names = {"DEBUG", "INFO", "WARN", "ERR", "FATAL"};
	private static final SimpleDateFormat format = new SimpleDateFormat("kk:mm:ss.SSS");
	
	static{
		int len = 0;
		for(String name : names) len = Math.max(len, name.length());
		for(int i=0; i<names.length; i++){
			int numSpaces = len - names[i].length();
			if(numSpaces == 0) continue;
			char[] spaces = new char[numSpaces];
			Arrays.fill(spaces, ' ');
			names[i] = names[i].concat(new String(spaces));
		}
		LOG_DIR.mkdirs();
	}
	
	private File file, latestFile;
	private PrintStream[] outs;
	public Log() throws FileNotFoundException{
		int i = 0;
		do{
			file = new File(LOG_DIR, "log" + i++ + ".txt");
		}while(file.exists());
		latestFile = new File(LOG_DIR, "latest.txt");
		outs = new PrintStream[]{System.out, new PrintStream(file), new PrintStream(latestFile)};
		write("Log initialized in " + file, DEBUG);
	}
	public void write(String message, int level){
		String time = format.format(Calendar.getInstance().getTime());
		String prefix = time + " [" + names[level] + "] ";
		message = prefix.concat(String.join("\r\n".concat(prefix), message.split("\\R")));
		for(PrintStream out : outs){
			out.println(message);
			out.flush();
		}
	}
	public void write(Throwable ex, int level){
		String time = format.format(Calendar.getInstance().getTime());
		StringBuilder sb = new StringBuilder(time).append(" [").append(names[level]).append("] ");
		ex.printStackTrace(new PrintWriter(new Writer(){
			public void write(char[] cbuf, int off, int len) throws IOException{
				for(char c : cbuf) sb.append(c);
			}
			public void flush() throws IOException{}
			public void close() throws IOException{}
		}));
		write(sb.toString(), level);
	}
	public void write(String message, Throwable ex, int level){
		write(message, level);
		write(ex, level);
	}
	public File getFile(){
		return file;
	}
}
