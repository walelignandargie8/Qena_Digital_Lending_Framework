import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "junit:target/cucumber-reports/Cucumber.xml",
                "json:target/cucumber.json",
                "html:target/site/cucumber-pretty"
        },
        glue = {"com.cxs.portal.steps"},
        features = {"src/test/resources/features"},
        monochrome = true
)
public class
TestRunner {

}
