import java.util.LinkedList;
import java.util.List;

class CityDevelopment {
    private Heap<Building> minHeap;
    private RedBlackTree<Integer, Building> redBlackTree;
    private LinkedList<Plan> plans;
    private Building currentBuilding;
    private int globalTimeCounter = -1;
    private int currentBuildingDevelopment = 5;

    public int getGlobalTimeCounter() {
        return globalTimeCounter;
    }

    CityDevelopment() {
        minHeap = new Heap<>(new ExecutionTimeComparator());
        redBlackTree = new RedBlackTree<>((a,b)-> a-b);
        plans = new LinkedList<>();
    }

    void addPlan(Plan plan) {
        plans.add(plan);
    }

    void incrementCounter() {
        doAction();
        globalTimeCounter++;
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
            if(currentBuilding.getExecutedTime() == currentBuilding.getTotalTime()) {
                System.out.println("("+ currentBuilding.getBuildingNumber() + ", " + globalTimeCounter + ")");
                redBlackTree.deleteElement(currentBuilding.getBuildingNumber());
                currentBuilding = null;
            }

        }
    }

    void doAction() {
        if(plans.size() > 0) {
            Plan firstPlan = plans.peekFirst();
            if(firstPlan.getStartingTime() == globalTimeCounter) {
                switch (firstPlan.getPlanType()) {
                    case INSERT:
                        addNewBuilding((InsertPlan) plans.pollFirst());
                        break;
                    case PRINT:
                        printBuildingTuple((PrintPlan) plans.pollFirst());
                        break;
                    case PRINT_RANGE:
                        printBuildingRangeTuples((PrintRangePlan) plans.pollFirst());
                        break;

                }
                doAction();
            }
        }
    }

    private boolean addNewBuilding(InsertPlan plan) {
        Building building = new Building(plan.getBuildingNumber(), plan.getTotalTime());
        boolean added = redBlackTree.addElement(building.getBuildingNumber(), building);
        if(!added) return false;
        minHeap.addElement(building);
        return true;
    }

    private void printBuildingTuple(PrintPlan printPlan) {
        Integer buildingNumber = printPlan.getBuildingNumber();
        Building building = redBlackTree.searchElement(buildingNumber);
        System.out.println("("+ building.getBuildingNumber() + ", " + building.getExecutedTime() + ", " + building.getTotalTime() + ")");
    }

    private void printBuildingRangeTuples(PrintRangePlan printRangePlan) {
        Integer buildingNumber1 = printRangePlan.getBuildingNumber1();
        Integer buildingNumber2 = printRangePlan.getBuildingNumber2();

        List<Building> buildings = redBlackTree.getElementsBetweenRange(buildingNumber1, buildingNumber2);
        for(int i=0; i < buildings.size(); i++) {
            Building building = buildings.get(i);
            System.out.print("("+ building.getBuildingNumber() + ", " + building.getExecutedTime() + ", " + building.getTotalTime() + ")");
            if(i < buildings.size()-1) {
                System.out.print(",");
            }
        }
        System.out.println();
    }

    boolean developmentDone() {
        return plans.size() == 0 && minHeap.length == 0 && currentBuilding == null;
    }
}
