package com.qa.opencart;


import com.qa.opencart.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test
    public void loginPageTitleTest()
    {
        Assert.assertEquals(loginPage.getLoginPageTitle(),
                Constants.LOGIN_PAGE_TITLE);
    }

    @Test
    public void loginPageUrlTest()
    {
        Assert.assertEquals(loginPage.getLoginPageUrl(),
                "https://naveenautomationlabs.com/opencart/index.php?route=account/login");
    }

    @Test
    public void forgotPasswordLinkTest()
    {
        Assert.assertTrue(loginPage.forgotPasswordLinkExist());
    }

    @Test
    public void customerContinueButtonExistTest()
    {
        Assert.assertTrue(loginPage.newCustomerContinueButtonDisplayed());
    }
    @Test
    public void loginTest()
    {
        accountsPage=loginPage.login(prop.getProperty("email"),prop.getProperty("pass"));
        Assert.assertTrue(accountsPage.logOutLinkExist());

    }
    /*
        Negative Test Scenario
     */
    @Test(enabled = false)
    public void invalidLoginTest()
    {
        Assert.assertEquals(loginPage.invalidLogin("family123@test.com","1234"),
                "Warning: No match for E-Mail Address and/or Password.");
    }
}
