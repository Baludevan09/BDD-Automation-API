package reporting

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import com.kms.katalon.core.configuration.RunConfiguration

class ExecutionManager {

    static Map currentRun = [:]

    static void startExecution() {

        currentRun = [
                runDate : new Date().format("yyyy-MM-dd HH:mm:ss"),
                scenarios : [],
                passed : 0,
                failed : 0,

                // Katalon report folder (THIS IS IMPORTANT)
                katalonReportPath : RunConfiguration.getReportFolder(),

                cucumberReportPath : ""
        ]

        println("Report Folder Stored => " + currentRun.katalonReportPath)
    }

    static void addScenario(String scenarioName) {

        currentRun.scenarios.add([
                name : scenarioName,
                status : "",
                startTime : System.currentTimeMillis()
        ])
    }

    static void updateScenarioStatus(String status) {

        def scenario = currentRun.scenarios.last()

        scenario.status = status

        scenario.duration =
                System.currentTimeMillis() - scenario.startTime

        if (status.equalsIgnoreCase("PASSED")) {
            currentRun.passed++
        } else {
            currentRun.failed++
        }
    }

    static void saveExecution() {

        File reportsDir = new File("Reports")

        String cucumberPath = ""

        // 🔥 Find cucumber HTML report
        reportsDir.eachFileRecurse { file ->

            if (file.name.toLowerCase().contains("index.html") &&
                    file.absolutePath.toLowerCase().contains("cucumber")) {

                cucumberPath = file.absolutePath.replace("\\", "/")
            }
        }

        currentRun.cucumberReportPath = cucumberPath

        File historyFile = new File("Reports/history/history.json")

        List history = []

        if (historyFile.exists()) {
            history = new JsonSlurper().parse(historyFile)
        }

        history.add(0, currentRun)

        historyFile.parentFile.mkdirs()

        historyFile.text = JsonOutput.prettyPrint(
                JsonOutput.toJson(history)
        )
    }
}