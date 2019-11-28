/*
 * PlanType enum to represent type of the input operation.
 */
enum PlanType {
    INSERT, PRINT, PRINT_RANGE
}

/*
 * Plan class to store input instructions according to the PlanType.
 */
public class Plan {
    private int startingTime;
    private PlanType planType;

    protected Plan(int startingTime, PlanType planType) {
        this.startingTime = startingTime;
        this.planType = planType;
    }

    public int getStartingTime() {
        return startingTime;
    }

    public PlanType getPlanType() {
        return planType;
    }
}

/*
 * InsertPlan class to store input instructions of type Insert.
 */
class InsertPlan extends Plan {
    private int buildingNumber;
    private int totalTime;

    public InsertPlan(int startingTime, int buildingNumber, int totalTime) {
        super(startingTime, PlanType.INSERT);
        this.buildingNumber = buildingNumber;
        this.totalTime = totalTime;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public int getTotalTime() {
        return totalTime;
    }
}

/*
 * PrintPlan class to store input instructions of type PrintBuilding operation with single argument.
 * This is equivalent to Print one building operation.
 */
class PrintPlan extends Plan {
    private int buildingNumber;

    public PrintPlan(int startingTime, int buildingNumber) {
        super(startingTime, PlanType.PRINT);
        this.buildingNumber = buildingNumber;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }
}

/*
 * PrintPlan class to store input instructions of type PrintBuilding operation with two arguments.
 * This is equivalent to Print buildings between range of Building Numbers.
 */
class PrintRangePlan extends Plan {
    private int buildingNumber1;
    private int buildingNumber2;

    public PrintRangePlan(int startingTime, int buildingNumber1, int buildingNumber2) {
        super(startingTime, PlanType.PRINT_RANGE);
        this.buildingNumber1 = buildingNumber1;
        this.buildingNumber2 = buildingNumber2;
    }

    public int getBuildingNumber1() {
        return buildingNumber1;
    }

    public int getBuildingNumber2() {
        return buildingNumber2;
    }
}