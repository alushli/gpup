package dtoObjects;

import java.util.Objects;

public class TargetRunSimulationSum {
    private String HMS;
    private String result;
    private String name;


    public TargetRunSimulationSum(String HMS, String result, String name) {
        this.HMS = HMS;
        this.result = result;
        this.name = name;
    }

    public void setHMS(String HMS) {
        this.HMS = HMS;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHMS() {
        return HMS;
    }

    public String getResult() {
        return result;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Target:" + this.name+ " ran for: " + HMS+" and end with status: " + result + "\n\r";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetRunSimulationSum that = (TargetRunSimulationSum) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
