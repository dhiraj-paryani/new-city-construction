import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class risingCity {
    public static void main(String[] args) throws FileNotFoundException {

        CityDevelopment newCity = new CityDevelopment();

        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("/" + args[0]);

        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        PrintStream outputFile = new PrintStream(new File("output_file.txt"));
        System.setOut(outputFile);

        while (sc.hasNext()) {
            String nextInput = sc.nextLine();
            nextInput = nextInput.trim();
            String[] inputParts = nextInput.split(":");
            int startingTime = Integer.parseInt(inputParts[0]);
            inputParts[1] = inputParts[1].trim();
            String[] actionParts = inputParts[1].split("\\(");
            String[] numbers = actionParts[1].split(",");
            if(actionParts[0].equals("Insert")) {
                numbers[1] = numbers[1].substring(0, numbers[1].length()-1);
                // System.out.println(startingTime + ":" + "Insert - " + Integer.parseInt(numbers[0]) + ", " + Integer.parseInt(numbers[1]));
                newCity.addPlan(new InsertPlan(startingTime, Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])));
            } else if(actionParts[0].equals("PrintBuilding")) {
                if (numbers.length == 1) {
                    numbers[0] = numbers[0].substring(0, numbers[0].length()-1);
                    // System.out.println(startingTime + ":" + "PrintPlan - " + Integer.parseInt(numbers[0]));
                    newCity.addPlan(new PrintPlan(startingTime, Integer.parseInt(numbers[0])));
                } else {
                    numbers[1] = numbers[1].substring(0, numbers[1].length()-1);
                    // System.out.println(startingTime + ":" + "PrintRangePlan - " + Integer.parseInt(numbers[0]) + ", " + Integer.parseInt(numbers[1]));
                    newCity.addPlan(new PrintRangePlan(startingTime, Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])));
                }
            }
        }

        while(!newCity.developmentDone()) {
            newCity.incrementCounter();
        }
        System.out.print(newCity.getGlobalTimeCounter());
    }
}
