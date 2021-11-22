package dtoObjects;

import java.util.Objects;

public class TargetRunSimulationSum {
    private String HMS;
    private String result;
    private String name;

    /* constructor */
    public TargetRunSimulationSum(String HMS, String result, String name) {
        this.HMS = HMS;
        this.result = result;
        this.name = name;
    }

    /* the function update HMS property */
    public void setHMS(String HMS) {
        this.HMS = HMS;
    }

    /* the function update result property */
    public void setResult(String result) {
        this.result = result;
    }

    /* the function update name property */
    public void setName(String name) {
        this.name = name;
    }

    /* the function return HMS */
    public String getHMS() {
        return HMS;
    }

    /* the function return result */
    public String getResult() {
        return result;
    }

    /* the function return name */
    public String getName() {
        return name;
    }

    @Override
    /* the function return to string of the class */
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
