package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchCarTests extends TestBase{

@Test
    public void searchCurrentMonthSuccess(){
    app.getHelperCar().searchCurrentMonth("Rehovot","3/10/2025","3/27/2025");
    app.getHelperCar().submit();
    Assert.assertTrue(app.getHelperCar().isListOfCarsAppeared());
}

    @Test
    public void searchCurrentYearSuccess(){
        app.getHelperCar().searchCurrentYear("Rehovot","4/27/2025","6/28/2025");
        app.getHelperCar().submit();
         Assert.assertTrue(app.getHelperCar().isListOfCarsAppeared());
    }

    @Test
    public void searchAnyPeriodSuccess(){
        app.getHelperCar().searchAnyPeriod("Rehovot","11/15/2025","2/10/2026");
        app.getHelperCar().submit();
         Assert.assertTrue(app.getHelperCar().isListOfCarsAppeared());
    }
}
