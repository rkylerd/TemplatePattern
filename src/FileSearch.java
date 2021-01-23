import java.io.*;
import java.util.*;
import java.util.regex.*;

public class FileSearch extends Searcher {

	private Matcher _searchMatcher;

	public FileSearch(String dirName, String filePattern, String searchPattern, boolean recurse) {

		super(dirName, filePattern, recurse);
		_searchMatcher = Pattern.compile(searchPattern).matcher("");

		fileProcessor(new File(_directory));

		System.out.println("\nTOTAL MATCHES: " + _total);
	}

	public void searchFile(File file) {



	}

    public void updateStatus() {
        if (_counter > 0) {
            System.out.println("MATCHES: " + _counter);
        }
    }

	public void handleInput(String line, File file) {
        _searchMatcher.reset(line);
        if (_searchMatcher.find()) {
            if (++_counter == 1) {
                System.out.println("");
                System.out.println("FILE: " + file);
            }

            System.out.println(line);
            ++_total;
        }
    }

	public static void main(String[] args) {
		
		String dirName = "";
		String filePattern = "";
		String searchPattern = "";
		boolean recurse = false;
		
		if (args.length == 3) {
			recurse = false;
			dirName = args[0];
			filePattern = args[1];
			searchPattern = args[2];
		}
		else if (args.length == 4 && args[0].equals("-r")) {
			recurse = true;
			dirName = args[1];
			filePattern = args[2];
			searchPattern = args[3];
		}
		else {
			usage();
			return;
		}
		
		new FileSearch(dirName, filePattern, searchPattern, recurse);
	}
	
	private static void usage() {
		System.out.println("USAGE: java FileSearch {-r} <dir> <file-pattern> <search-pattern>");
	}

}
