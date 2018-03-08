package model;

public class ApiKey {
    
    private String apiKey;
    private String description = "Undefined";
    private int timesCalled = 0; //For last x seconds (x = apiIntervalSeconds).
    private boolean isValid = false;

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getTimesCalled()
    {
        return timesCalled;
    }

    public void setTimesCalled(int timesCalled)
    {
        this.timesCalled = timesCalled;
    }

    public boolean getIsValid()
    {
        return isValid;
    }

    public void setIsValid(boolean isValid)
    {
        this.isValid = isValid;
    }
   
    
}
