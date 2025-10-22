import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {

    public static void main(String[] args) throws IOException {

        File path = new File(".");
        String[] fileList = path.list((d, n) -> n.endsWith(".txt"));
        if (fileList == null) {                                                     //this is a simple check if a .txt is available or not... not crazy
            System.out.println("No .txt file found. Fix that.");
            return;
        }


        List<String> files = Arrays.stream(fileList).sorted().collect(Collectors.toList()); //sorts the files nothing crazy yet 

        for (String inputFile : files) {                                                    //this entire for loop is gonna be reading all content inside of all files cleaning then up if there is any unnecessary stuff on them
            System.out.println("===" + inputFile + "===");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
            reader.close();








           
            int maxState = 0;                               //finds the fattest state number in this NFA
            for (String current : lines) {
                String[] parts = current.split(",");
                int state = Integer.parseInt(parts[0].trim());
                if (state > maxState) maxState = state;
            }

            // Build transitions
            int[][] transitions = new int[maxState + 1][];              //admittedly I did look up a github that dealt with transitions for NFA's, but I did not fully understand the issues
                                                                        //so I also asked the github AI about how it works 
                                                                        //Funnily enough drawing an actual NFA helped a lot more since this process of finding the transitions is the following what state touches what similar to one of the Assignments DFA to NFA
            for (String current : lines) {
                String[] parts = current.split(",");
                int state = Integer.parseInt(parts[0].trim());
                String right = parts[1].trim().replace("{", "").replace("}", "");

                if (right.equalsIgnoreCase("empty")) {
                    transitions[state] = new int[0];
                } else {
                    String[] nums = right.split(",");
                    int[] arr = new int[nums.length];
                    for (int innie = 0; innie < nums.length; innie++) {
                        try {
                            arr[innie] = Integer.parseInt(nums[innie].trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Warning: ignoring invalid token '" + nums[innie] + "'");
                        }
                    }
                    transitions[state] = arr;
                }
            }

           
            for (int state = 1; state <= maxState; state++) {
                boolean[] visited = new boolean[maxState + 1];
                int[] result = new int[maxState + 1];
                epsilonClosure(state, transitions, visited, result);

                System.out.print("E(" + state + ") = {");
                boolean first = true;
                for (int innie = 1; innie <= maxState; innie++) {
                    if (result[innie] == 1) {
                        if (!first) System.out.print(",");
                        System.out.print(innie);
                        first = false;
                    }
                }
                System.out.println("}");
            }

            System.out.println();
        }
    }

    public static void epsilonClosure(int state, int[][] transitions, boolean[] visited, int[] result) {
        if (visited[state]) return;
        visited[state] = true;
        result[state] = 1;
        for (int next : transitions[state]) {
            epsilonClosure(next, transitions, visited, result);
        }
    }
}

