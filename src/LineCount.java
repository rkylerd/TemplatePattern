import java.io.*;
import java.util.*;
import java.util.regex.*;
//Counts the number of lines in a file
public class LineCount extends Searcher {

	
	public LineCount(String directory, String pattern, boolean recurse) {
		super(directory, pattern, recurse);


	}
	
	private void run() {
		fileProcessor(new File(_directory));
		System.out.println("TOTAL: " + _total);
	}

	public void searchFile(File file) {
		String fileName = getFileName(file);
		_fileMatcher.reset(fileName);
		if (_fileMatcher.find()) {
			try {
				data = new BufferedInputStream(new FileInputStream(file));
				int curLineCount = 0;
				try {
					curLineCount = 0;		
					Scanner input = new Scanner(data);
					while (input.hasNextLine()) {
						String line = input.nextLine();											

					}
				}
				finally {
					System.out.println(curLineCount + "  " + file);					
					data.close();
				}
			}
			catch (IOException e) {
				unreadableFile(file);
			}
		}
	}
	public void updateStatus() {
		if (_counter > 0) {
			System.out.println("MATCHES: " + _counter);
		}
	}

	public void handleInput(String line, File file) {
		++_counter;
		++_total;
	}

	public static void main(String[] args) {
		String directory = "";
		String pattern = "";
		boolean recurse = false;
		
		if (args.length == 2) {
			recurse = false;
			directory = args[0];
			pattern = args[1];
		}
		else if (args.length == 3 && args[0].equals("-r")) {
			recurse = true;
			directory = args[1];
			pattern = args[2];
		}
		else {
			usage();
			return;
		}
		
		LineCount lineCounter = new LineCount(directory, pattern, recurse);
		lineCounter.run();
	}
	
	private static void usage() {
		System.out.println("USAGE: java LineCount {-r} <dir> <file-pattern>");
	}

}
