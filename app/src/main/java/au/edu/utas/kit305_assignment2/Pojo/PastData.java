package au.edu.utas.kit305_assignment2.Pojo;

/**
 * Created by adarshan on 5/13/16.
 */
public class PastData
{
    private int id;
    private String foodGroup, foodType, serving, date, mealTime, quantity ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodGroup()
    {
        return foodGroup;
    }

    public void setFoodGroup(String foodGroup)
    {
        this.foodGroup = foodGroup;
    }

    public String getFoodType()
    {
        return foodType;
    }

    public void setFoodType(String foodType)
    {
        this.foodType = foodType;
    }

    public String getServing()
    {
        return serving;
    }

    public void setServing(String serving)
    {
        this.serving = serving;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getMealTime()
    {
        return mealTime;
    }

    public void setMealTime(String mealTime)
    {
        this.mealTime = mealTime;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

}
