package stepdefination

import io.cucumber.java.Before
import io.cucumber.java.After
import io.cucumber.java.Scenario

import reporting.ExecutionManager

class Hooks {

    static boolean started = false

    @Before
    void BeforeScenario(Scenario scenario) {

        if(!started) {

            ExecutionManager.startExecution()

            started = true
        }

        ExecutionManager.addScenario(
                scenario.getName())

        println("Starting Scenario : "
                + scenario.getName())
    }

    @After
    void AfterScenario(Scenario scenario) {

        ExecutionManager.updateScenarioStatus(
                scenario.getStatus().toString())

        println("Ending Scenario : "
                + scenario.getName())
    }
}