package com.qa.opencart;

import com.qa.opencart.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

public class RegisterPageTest extends BaseTest{

    @BeforeClass
    public void regPageSetUp()
    {
        registerPage=loginPage.navigateToRegisterPage();

    }

    @Test
    public void registerPageTitleTest()
    {
        String actualTitle=registerPage.getRegisterPageTitle();
        Assert.assertEquals(actualTitle,Constants.REGISTER_PAGE_TITLE);
    }

    @Test
    public void registerPageHeaderTextTest()
    {
        String actualText=registerPage.getRegisterAccountHeaderText();
        Assert.assertEquals(actualText,Constants.REGISTER_PAGE_HEADER_TEXT);
    }

    @Test
    public void registerPageHeaderLabelsTest()
    {
        List<String> actualFieldSetHeaderList=registerPage.getFieldSetHeaderTextList();
        Assert.assertEquals(actualFieldSetHeaderList,Constants.REGISTER_PAGE_FIELDSET_HEADERS_TEXT_LIST);
    }

    @Test
    public void registerPageLinkTest()
    {
        Assert.assertTrue(registerPage.loginPageHyperLinkExist());
    }
    @Test
    public void registerPageWebElementsTest()
    {
        Assert.assertTrue(registerPage.yesRadioButtonExist());
        Assert.assertTrue(registerPage.noRadioButtonExist());
        Assert.assertTrue(registerPage.agreeCheckBoxExist());
    }

    @Test
    public void registerPageWebElementsLabelsTest()
    {
        List<String> actualLabelTextList=registerPage.getLabelElementsText();
        Assert.assertEquals(actualLabelTextList,Constants.REGISTER_PAGE_LABELS_TEXT_LIST);
    }
    public String getRandomEmail() {
        return "user" + UUID.randomUUID().toString().substring(0, 6) + "@test.com";
    }
    @DataProvider
    public Object[][] getUserData()
    {
        return new Object[][]{
                {   "Ramu5","Somu5","flatmate34193@test.com","21232","testadmin","yes"},
                {
                    "Ramu6","Somu6","goodmat34193@test.com","21232","testadmin","no"}
        };

    }

    @Test(dataProvider ="getUserData")
    public void newUserRegisterTest(String fname,String lname,
                                    String email,String phone,
                                    String pass, String sub)
    {
        Assert.assertTrue(registerPage.newUserRegister(fname, lname, email,
                phone, pass,sub));
    }
}
