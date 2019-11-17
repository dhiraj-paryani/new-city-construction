enum PlanType {
    INSERT, PRINT, PRINT_RANGE
}

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