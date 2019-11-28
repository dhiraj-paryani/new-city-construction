import java.util.LinkedList;
import java.util.List;

/*
 * CityDevelopment class which will develop one city and
 * take care of all the building parameters and development conditions
 */
class CityDevelopment {
    private Heap<Building> minHeap;
    private RedBlackTree<Integer, Building> redBlackTree;
    private LinkedList<Plan> insertPlans;
    private LinkedList<Plan> printPlans;
    private Building currentBuilding;
    private int globalTimeCounter = -1;
    private int currentBuildingDevelopment = 0;

    public CityDevelopment() {
        minHeap = new Heap<>(new NextConstructionComparator());
        redBlackTree = new RedBlackTree<>((a,b)-> a-b);
        insertPlans = new LinkedList<>();
        printPlans = new LinkedList<>();
    }

    /*
     * Add insert plan to insert the input type of insert.
     */
    public void addInsertPlan(Plan plan) {
        insertPlans.add(plan);
    }

    /*
     * Add insert plan to insert the input type of PrintBuilding.
     */
    public void addPrintPlan(Plan plan) {
        printPlans.add(plan);
    }

    /*
     * Following mehtod will increment the counter and run the development of relevant building.
     */
    public void incrementCounter() {
        ++globalTimeCounter;
        addInsertPlanToCurrentBuilding();
        printPlansToOuputFile();
        if(currentBuilding != null && currentBuilding.getExecutedTime() == currentBuilding.getTotalTime()) {
            System.out.println("("+ currentBuilding.getBuildingNumber() + "," + (globalTimeCounter) + ")");
            redBlackTree.deleteElement(currentBuilding.getBuildingNumber());
            currentBuildingDevelopment = 0;
            currentBuilding = null;
        }
        if(currentBuilding != null && currentBuildingDevelopment == 5) {
            currentBuildingDevelopment = 0;
            minHeap.addElement(currentBuilding);
            currentBuilding = minHeap.poll();
        }
        if(currentBuilding == null) {
            currentBuildingDevelopment = 0;
            currentBuilding = minHeap.poll();
        }
        if(currentBuilding != null) {
            currentBuildingDevelopment++;
            currentBuilding.setExecutedTime(currentBuilding.getExecutedTime()+1);
        }
    }

    private void addInsertPlanToCurrentBuilding() {
        if(insertPlans.size() > 0) {
            Plan firstPlan = insertPlans.peekFirst();
            if(firstPlan.getStartingTime() == globalTimeCounter) {
                addNewBuilding((InsertPlan) insertPlans.pollFirst());
                addInsertPlanToCurrentBuilding();
            }
        }
    }

    private void printPlansToOuputFile() {
        // System.out.println("printPlansToOuputFile called" + globalTimeCounter);
        if(printPlans.size() > 0) {
            Plan firstPlan = printPlans.peekFirst();
            if (firstPlan.getStartingTime() == globalTimeCounter) {
                switch (firstPlan.getPlanType()) {
                    case PRINT:
                        printBuildingTuple((PrintPlan) printPlans.pollFirst());
                        break;
                    case PRINT_RANGE:
                        printBuildingRangeTuples((PrintRangePlan) printPlans.pollFirst());
                        break;
                }
                printPlansToOuputFile();
            }
        }
    }

    private boolean addNewBuilding(InsertPlan plan) {
        Building building = new Building(plan.getBuildingNumber(), plan.getTotalTime());
        boolean added = redBlackTree.addElement(building.getBuildingNumber(), building);
        if(!added) {
            System.out.println("Not added");
            return false;
        }
        minHeap.addElement(building);
        return true;
    }

    private void printBuildingTuple(PrintPlan printPlan) {
        Integer buildingNumber = printPlan.getBuildingNumber();
        Building building = redBlackTree.searchElement(buildingNumber);
        if(building == null) {
            System.out.println("(0,0,0)");
        } else {
            System.out.println("("+ building.getBuildingNumber() + "," + building.getExecutedTime() + "," + building.getTotalTime() + ")");
        }
    }

    private void printBuildingRangeTuples(PrintRangePlan printRangePlan) {
        Integer buildingNumber1 = printRangePlan.getBuildingNumber1();
        Integer buildingNumber2 = printRangePlan.getBuildingNumber2();

        List<Building> buildings = redBlackTree.getElementsBetweenRange(buildingNumber1, buildingNumber2);
        if(buildings.size() > 0) {
            for(int i=0; i < buildings.size(); i++) {
                Building building = buildings.get(i);
                System.out.print("("+ building.getBuildingNumber() + "," + building.getExecutedTime() + "," + building.getTotalTime() + ")");
                if(i < buildings.size()-1) {
                    System.out.print(",");
                }
            }
            System.out.println();
        } else {
            System.out.println("(0,0,0)");
        }
    }

    /*
     * Return flag which represents city development is done or not.
     */
    public boolean developmentDone() {
            return insertPlans.size() == 0 && printPlans.size() == 0 && minHeap.length == 0 && redBlackTree.head == null && currentBuilding == null;
    }
}
