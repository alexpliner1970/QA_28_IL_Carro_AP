package tests;

import manager.DataProviderUser;
import models.User;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

public class RegistrationTests extends TestBase {

    @BeforeMethod
    public void preCondition() {
        if (app.getHelperUser().isLogged())
            app.getHelperUser().logout();
    }

    @Test
    public void registrationSuccess() {
        Random random = new Random();
        int i = random.nextInt(1000) + 1000;
        System.out.println(i);


        System.out.println(System.currentTimeMillis());
        int z = (int) ((System.currentTimeMillis() / 1000) % 3600);
        System.out.println(z);

        User user = new User()
                .setFirsName("Lisa")
                .setLastName("Snow")
                .setEmail("snow" + i + "@gmail.com")
                .setPassword("Snow123456$");

        app.getHelperUser().openRegistrationForm();
        app.getHelperUser().fillRegistrationForm(user);

       app.getHelperUser().checkPolicyXY();
       app.getHelperUser().submit();
       Assert.assertEquals(app.getHelperUser().getMessage(),"You are logged in success");


    }

    @AfterMethod
    public void postCondition(){
        app.getHelperUser().pause(1000);
        app.getHelperUser().clickOkButton();
    }
//=======================negative=================================
    @Test
    public void registrationEmptyEmail(){
        User user = new User()
                .setFirsName("Lisa")
                .setLastName("Snow")
                .setEmail("")
                .setPassword("Snow123456$");

        app.getHelperUser().openRegistrationForm();
        app.getHelperUser().fillRegistrationForm(user);
        app.getHelperUser().checkPolicy();
        app.getHelperUser().submit();
        Assert.assertEquals(app.getHelperUser().getErrorText(), "Email is required");
        Assert.assertTrue(app.getHelperUser().isYallaButtonNotActive());

    }
    @Test
    public void registrationEmptyPassword(){
        User user = new User()
                .setFirsName("Lisa")
                .setLastName("Snow")
                .setEmail("sdf@wt.com")
                .setPassword("");
        app.getHelperUser().openRegistrationForm();
        app.getHelperUser().fillRegistrationForm(user);
        app.getHelperUser().checkPolicy();
        app.getHelperUser().submit();
        Assert.assertEquals(app.getHelperUser().getErrorText(), "Password is required");
        Assert.assertTrue(app.getHelperUser().isYallaButtonNotActive());
    }

    @Test
    public void registrationWrongPassword(){
        User user = new User()
                .setFirsName("Lisa")
                .setLastName("Snow")
                .setEmail("sdf@wt.com")
                .setPassword("Sn");
        app.getHelperUser().openRegistrationForm();
        app.getHelperUser().fillRegistrationForm(user);
        app.getHelperUser().checkPolicy();
        app.getHelperUser().submit();
        Assert.assertEquals(app.getHelperUser().getErrorText(), "Password must contain minimum 8 symbols\n" +
                "Password must contain 1 uppercase letter, 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]");
        Assert.assertTrue(app.getHelperUser().isYallaButtonNotActive());
    }
    @Test
    public void registrationWrongEmail(){
        User user = new User()
                .setFirsName("Lisa")
                .setLastName("Snow")
                .setEmail("sdfwt.")
                .setPassword("Snow123456$");
        app.getHelperUser().openRegistrationForm();
        app.getHelperUser().fillRegistrationForm(user);
        app.getHelperUser().checkPolicy();
        app.getHelperUser().submit();
        //Assert.assertEquals(app.getHelperUser().getErrorText(),"Wrong email format\n" +
                //"Wrong email format");
        Assert.assertTrue(app.getHelperUser().getErrorText().contains("Wrong email format"));
        Assert.assertTrue(app.getHelperUser().isYallaButtonNotActive());

    }

    @Test
    public void registrationEmptyName() {
        User user = new User()
                .setFirsName("")
                .setLastName("Snow")
                .setEmail("sdf@wt.com")
                .setPassword("Snow123456$");
        app.getHelperUser().openRegistrationForm();
        app.getHelperUser().fillRegistrationForm(user);
        app.getHelperUser().checkPolicy();
        app.getHelperUser().submit();
        Assert.assertEquals(app.getHelperUser().getErrorText(), "Name is required");
        Assert.assertTrue(app.getHelperUser().isYallaButtonNotActive());
    }

    @Test
    public void registrationEmptyLastName() {
        User user = new User()
                .setFirsName("Regina")
                .setLastName("")
                .setEmail("sdf@wt.com")
                .setPassword("Snow123456$");
        app.getHelperUser().openRegistrationForm();
        app.getHelperUser().fillRegistrationForm(user);
        app.getHelperUser().checkPolicy();
        app.getHelperUser().submit();
        Assert.assertEquals(app.getHelperUser().getErrorText(), "Last name is required");
        Assert.assertTrue(app.getHelperUser().isYallaButtonNotActive());
    }
    @Test
    public void registrationNoEnteredCheckbox() {
        User user = new User()
                .setFirsName("Regina")
                .setLastName("Snow")
                .setEmail("sdf@wt.com")
                .setPassword("Snow123456$");
        app.getHelperUser().openRegistrationForm();
        app.getHelperUser().fillRegistrationForm(user);
        app.getHelperUser().submit();
        app.getHelperUser().pause(2000);
        Assert.assertTrue(app.getHelperUser().isYallaButtonNotActive());
    }

    @Test
    public void registrationRegisteredUser(){
        User user = new User()
                .setFirsName("Regina")
                .setLastName("Snow")
                .setEmail("marga@gmail.com")
                .setPassword("Snow123456$");
        app.getHelperUser().openRegistrationForm();
        app.getHelperUser().fillRegistrationForm(user);

        app.getHelperUser().checkPolicy();
        app.getHelperUser().submit();

        Assert.assertEquals(app.getHelperUser().getMessage(),"\"User already exists\"");

    }

    @Test(dataProvider = "registrationFile",dataProviderClass = DataProviderUser.class)
    public void registrationSuccessAllFieldsCSV(User user) {


        app.getHelperUser().openRegistrationForm();
        app.getHelperUser().fillRegistrationForm(user);

        app.getHelperUser().checkPolicyXY();
        app.getHelperUser().submit();
        Assert.assertEquals(app.getHelperUser().getMessage(),"You are logged in success");


    }

}
