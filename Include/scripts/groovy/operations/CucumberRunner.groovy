package operations

import org.junit.runner.RunWith
import io.cucumber.junit.CucumberOptions
import io.cucumber.junit.Cucumber

@RunWith(Cucumber.class)
@CucumberOptions(
features = "Include/features/Signup.feature",
glue = "operations",
plugin = [
	"pretty",
	"html:ReportsFolder/cucumber-default-report",
	"json:ReportsFolder/cucumber.json"
]
)
public class CucumberRunner {
	// Keep clean
}