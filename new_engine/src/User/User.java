package User;

public abstract class User {
    protected String name;
    protected boolean isActive;

    public String getName() {
        return name;
    }

    public boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }
}
