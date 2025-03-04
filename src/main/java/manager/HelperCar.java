package manager;

import models.Car;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HelperCar extends HelperBase{
    public HelperCar(WebDriver wd) {
        super(wd);
    }

    public void openCarForm() {
        pause(500);
        click(By.xpath("//a[text()=' Let the car work ']"));
    }
    public void fillCarForm(Car car){
        typeLocation(car.getLocation());
        type(By.id("make"), car.getManufacture());
        type(By.id("model"), car.getModel());
        type(By.id("year"), car.getYear());
        select(By.id("fuel"),car.getFuel());
        type(By.id("seats"),String.valueOf(car.getSeats()));
        type(By.id("class"),car.getCarClass());
        type(By.id("serialNumber"),car.getCarRegNum());
        type(By.id("price"),car.getPrice()+"");
        type(By.id("about"), car.getAbout());
    }

    private void typeLocation(String location) {
        type(By.id("pickUpPlace"),location);
        click(By.cssSelector("div.pac-item"));

    }
    private void select(By locator, String option) {
        Select select = new Select(wd.findElement(locator));
        select.selectByValue(option);
//        select.selectByIndex(5);
//        select.selectByValue("Gas");
//        select.selectByVisibleText(" Gas ");

    }
    public void attachPhoto(String link) {
        wd.findElement(By.id("photos")).sendKeys(link);
    }
    public void returnToHomePage() {
        click(By.xpath("//button[text()='Search cars']"));

    }

    public void searchCurrentMonth(String city, String dateFrom, String dateTo) {
        typeCity(city);
        click(By.id("dates"));
        String[]from=dateFrom.split("/");
        String locatorFrom= "//div[text()= ' "+from[1]+" ']";

        click(By.xpath(locatorFrom));

        String[]to=dateTo.split("/");


        click(By.xpath("//div[text()= ' "+to[1]+" ']"));


    }

    private void typeCity(String city) {
        type(By.id("city"),city);
        click(By.cssSelector("div.pac-item"));

    }


    public void searchCurrentYear(String city, String dateFrom, String dateTo) {
        typeCity(city);
        click(By.id("dates"));
        //"4/27/2025","6/28/2025"

       LocalDate now= LocalDate.now();
       int year= now.getYear();
       int month= now.getMonthValue();
       int day=now.getDayOfMonth();

       LocalDate from =LocalDate.parse(dateFrom, DateTimeFormatter.ofPattern("M/d/yyyy"));
       int diffMonth=from.getMonthValue()-month;
       if(diffMonth>0)
           clickNextMonthBtn(diffMonth);

        click(By.xpath("//div[text()= ' "+from.getDayOfMonth()+" ']"));
        pause(1500);

        LocalDate to =LocalDate.parse(dateTo, DateTimeFormatter.ofPattern("M/d/yyyy"));
        diffMonth=to.getMonthValue()-from.getMonthValue();

        if(diffMonth>0)
            clickNextMonthBtn(diffMonth);

        click(By.xpath("//div[text()= ' "+to.getDayOfMonth()+" ']"));

    }

    private void clickNextMonthBtn(int diffMonth) {
        for (int i = 0; i < diffMonth; i++) {
            click(By.cssSelector("button[aria-label='Next month']"));

        }
    }


    public boolean isListOfCarsAppeared() {
        return isElementPresent(By.cssSelector("a.car-container"));
    }

    public void searchAnyPeriod(String city, String dateFrom, String dateTo) {
        typeCity(city);
        click(By.id("dates"));
        String v= wd.findElement(By.cssSelector("button[aria-label='Choose month and year'] span[class='mat-button-wrapper'")).getText();
        //System.out.println(v);
        String[]from=dateFrom.split("/");

        if(!(from[2].contains(v.split(" ")[1]))){
            click(By.cssSelector("button[aria-label='Choose month and year'] span[class='mat-button-wrapper'"));
            List<WebElement> years =wd.findElements(By.cssSelector("div.mat-calendar-body-cell-content"));
            for (WebElement element:years){
                if(element.getText().equals(from[2]))
                    element.click();
            }

        }
            searchCurrentYear(city,dateFrom,dateTo);
    }
}

