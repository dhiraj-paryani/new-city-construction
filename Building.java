import java.util.Comparator;

class Building {
    private int buildingNumber;
    private int executedTime;
    private int totalTime;

    int getBuildingNumber() {
        return buildingNumber;
    }

    void setExecutedTime(int executedTime) {
        this.executedTime = executedTime;
    }

    int getExecutedTime() {
        return executedTime;
    }

    int getTotalTime() {
        return totalTime;
    }

    Building(int buildingNumber, int totalTime) {
        this(buildingNumber, 0, totalTime);
    }

    private Building(int buildingNumber, int executedTime, int totalTime) {
        this.buildingNumber = buildingNumber;
        this.executedTime = executedTime;
        this.totalTime = totalTime;
    }
}

class ExecutionTimeComparator implements Comparator<Building> {
    @Override
    public int compare(Building b1, Building b2) {
        int executedTimeDiff = b2.getExecutedTime() - b1.getExecutedTime();
        if (executedTimeDiff == 0) {
            return b2.getBuildingNumber() - b1.getBuildingNumber();
        } else {
            return executedTimeDiff;
        }
    }
}
