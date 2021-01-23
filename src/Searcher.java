import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Searcher {

    protected String _directory;
    protected String _pattern;
    protected boolean _recurse;
    protected int _total;
    protected int _counter;
    protected Matcher _fileMatcher;
    protected InputStream data;

    public Searcher(String directory, String pattern, boolean recurse) {
        _directory = directory;
        _pattern = pattern;
        _recurse = recurse;
        _counter = 0;
        _total = 0;
        _fileMatcher = Pattern.compile(_pattern).matcher("");
    }

    abstract public void updateStatus();
    abstract void handleInput(String line, File file);
    abstract void searchFile(File file);

    public void nonDir(File dir) {
        System.out.println(dir + " is not a directory");
    }

    public void unreadableDir(File dir) {
        System.out.println("Directory " + dir + " is unreadable");
    }

    public void unreadableFile(File file) {
        System.out.println("File " + file + " is unreadable");
    }

    protected String getFileName(File file) {
        try {
            return file.getCanonicalPath();
        }
        catch (IOException e) {
            return "";
        }
    }

    public void readFile(File file) {
        String fileName = getFileName(file);
        _fileMatcher.reset(fileName);
        if (_fileMatcher.find()) {
            try {
                int curMatches = 0;

                data = new BufferedInputStream(new FileInputStream(file));
                try {
                    Scanner input = new Scanner(data);
                    while (input.hasNextLine()) {
                        String line = input.nextLine();
                        handleInput(line, file);
                    }
                }
                finally {
                    data.close();
                    updateStatus();

                }
            }
            catch (IOException e) {
                unreadableFile(file);
            }
        }
    }

    public void fileProcessor(File dir) {
        if (dir.isDirectory())
        {
            if (dir.canRead())
            {
                for (File file : dir.listFiles()) {
                    if (file.isFile()) {
                        if (file.canRead()) {
                            searchFile(file);
                        }
                        else {
                            unreadableFile(file);
                        }
                    }
                }

                if (_recurse) {
                    for (File file : dir.listFiles()) {
                        if (file.isDirectory()) {
                            searchFile(file);
                        }
                    }
                }
            }
            else {
                unreadableDir(dir);
            }
        }
        else {
            nonDir(dir);
        }
    }


}
