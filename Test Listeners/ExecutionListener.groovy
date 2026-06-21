import com.kms.katalon.core.annotation.AfterTestSuite

import reporting.ExecutionManager
import reporting.ReportGenerator

class ExecutionListener {

    @AfterTestSuite
    def afterSuite(def suiteContext) {

        ExecutionManager.saveExecution()

        ReportGenerator.generate()

        println(
                "Consolidated Report Generated Successfully")
    }
}