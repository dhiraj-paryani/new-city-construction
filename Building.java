import java.util.Comparator;

/**
 * Building class (model) which has Building Number, Executed Time and Total Time.
 */
class Building {
    private int buildingNumber;
    private int executedTime;
    private int totalTime;

    public Building(int buildingNumber, int totalTime) {
        this(buildingNumber, 0, totalTime);
    }

    private Building(int buildingNumber, int executedTime, int totalTime) {
        this.buildingNumber = buildingNumber;
        this.executedTime = executedTime;
        this.totalTime = totalTime;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public int getExecutedTime() {
        return executedTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setExecutedTime(int executedTime) {
        this.executedTime = executedTime;
    }
}

/**
 * Comparator for heap. First check excuted time of the buildings if equal check building number.
 */
class NextConstructionComparator implements Comparator<Building> {
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
