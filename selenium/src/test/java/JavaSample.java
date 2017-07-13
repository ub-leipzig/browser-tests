import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class JavaSample extends BrowserStackJUnitTest {

    //private static final String USERNAME = "christopherhanna1";
    //private static final String AUTOMATE_KEY = "p67huTp2FsDENgqwzkaA";
    //private static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    @Test
    public void test() throws Exception {

     //   DesiredCapabilities caps = new DesiredCapabilities();
     //   caps.setCapability("browserName", "internet explorer");
     //   caps.setCapability("version", "11");
     //   caps.setCapability("browserstack.debug", "true");
     //   caps.setCapability("build", "First build");

     //   WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
        Integer timeoutLimitSeconds = 10;
        Assert.assertTrue(isElementPresent(driver,timeoutLimitSeconds));
        System.out.println(driver.getTitle());
        driver.quit();

    }

    private static boolean isElementPresent(WebDriver driver, Integer timeoutLimitSeconds) throws IOException, URISyntaxException {

        WebDriverWait wait = new WebDriverWait(driver, timeoutLimitSeconds);
        driver.get("http://test.dfg-viewer.de/show/?id=8071&tx_dlf%5Bid%5D=http%3A%2F%2Fiiif.ub.uni-leipzig.de%2F0000002170%2Fpresentation.xml&tx_dlf%5Bpage%5D=308");
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//canvas")));
            return true;
        } catch(TimeoutException e) {
            String session = ((RemoteWebDriver) driver).getSessionId().toString();
            mark(session);
            return false;
        }
    }

    public static void mark(String session) throws URISyntaxException, IOException {
        URI uri = new URI("https://christopherhanna1:p67huTp2FsDENgqwzkaA@www.browserstack.com/automate/sessions/" + session + ".json");
        HttpPut putRequest = new HttpPut(uri);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add((new BasicNameValuePair("status", "failed")));
        nameValuePairs.add((new BasicNameValuePair("reason", "not found")));
        putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpClientBuilder.create().build().execute(putRequest);
    }
}