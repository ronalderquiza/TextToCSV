/**
 * Created by Ronald Erquiza on 4/14/2016.
 */
import java.util.regex.*;
import java.util.Scanner;
import java.io.*;

public class TexttoCSV {
    public static void main(String[] args) throws Exception{

        String content = new Scanner(new File(args[0])).useDelimiter("\\Z").next();
        System.out.println("Cleaning...");
        content = newTextFile(content);
        try (PrintWriter out = new PrintWriter("reports.csv")) {
            out.println(content);
        }
        System.out.println("Done!");

    }

    public static String newTextFile(String textFile){
        String newText = new String();
        String pattern = "([-\\w]+)=\\{([^\\{}]+)}";
        String inPattern = "(\\w+)=(\\d+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(textFile);
        newText = newText + "name" + "," + "positive" + "," + "negative" + "," + "neutral" + "\n";
        while (m.find()) {
            String name = new String();
            String properties = new String();
            int[] values = new int[3];
            name = m.group(1);
            properties = m.group(2);
            Pattern r1 = Pattern.compile(inPattern);
            Matcher m1 = r1.matcher(properties);
            while(m1.find()){
                if(m1.group(1).equals("positive")){
                    values[0] = Integer.parseInt(m1.group(2));
                }
                if(m1.group(1).equals("negative")){
                    values[1] = Integer.parseInt(m1.group(2));
                }
                if(m1.group(1).equals("neutral")) {
                    values[2] = Integer.parseInt(m1.group(2));
                }
                if(m1.group(1).equals("unrelated")){
                    values[0] = Integer.parseInt(m1.group(2));
                }
            }

            newText = newText + name + "," + values[0] + "," + values[1] + "," + values[2] + "\n";
        }
        return newText;
    }

}
