import java.io.*;
import java.util.stream.*;
import java.util.*;

public class Main{
    public static void main(String[] rbh){
        File path = new File(".");
        List<String> files= Arrays.stream(path.list((d,n) -> 
            n.endsWith(".txt")))
                .sorted()
                .collect(Collectors.toList());

        for(String inputFile: files){
            System.out.println("===" + inputFile + "===");

            BufferedReader read = new BufferedReader(new FileReader(inputFile));
            List<String> lines = new ArrayList<>();

            String line;
            while ((line = read.readLine()) != null){
                if (!line.trim().isEmpty()){
                    lines.add(line.trim());
                }
            }


            read.close();


            for(String current : lines){
                List <String> part = Arrays.stream(current.replace("{", "")
                    .replace("}", "")
                    .split(",")).map(String::trim)
                    .collect(Collectors.toList());

                System.out.println(part);
            }
            System.out.println();
        }
    }
}