package reporting

import groovy.json.JsonSlurper

class ReportGenerator {

    static void generate() {

        File historyFile = new File("Reports/history/history.json")

        if (!historyFile.exists()) {
            println("No history file found")
            return
        }

        def history = new JsonSlurper().parse(historyFile)

        String html = """
<html>
<head>
<title>Consolidated Report</title>

<style>
body{
    font-family: Arial;
    margin:20px;
    background:#f5f5f5;
}

.card{
    background:white;
    padding:15px;
    margin-bottom:15px;
    border-radius:8px;
    box-shadow:0px 0px 5px #ccc;
}

.pass{ color:green; font-weight:bold; }
.fail{ color:red; font-weight:bold; }

a{
    text-decoration:none;
    color:#0066cc;
    font-weight:bold;
}
</style>

</head>

<body>

<h1>Consolidated Execution Report</h1>
"""

        history.each { run ->

            String katalonLink = ""
            String cucumberLink = ""

            if (run.katalonReportPath) {
                katalonLink = "file:///" + run.katalonReportPath.replace("\\", "/")
            }

            if (run.cucumberReportPath) {
                cucumberLink = "file:///" + run.cucumberReportPath.replace("\\", "/")
            }

            html += """
<div class="card">

<h3>${run.runDate}</h3>

<div class="pass">Passed : ${run.passed}</div>
<div class="fail">Failed : ${run.failed}</div>

<br>

<b>Katalon Report:</b><br>
${katalonLink ?
                    "<a href='${katalonLink}' target='_blank'>Open Katalon Report</a>"
                    : "Not Available"}

<br><br>

<b>Cucumber Report:</b><br>
${cucumberLink ?
                    "<a href='${cucumberLink}' target='_blank'>Open Cucumber Report</a>"
                    : "Not Available"}

</div>
"""
        }

        html += """
</body>
</html>
"""

        File output = new File("Reports/ConsolidatedReport.html")
        output.text = html

        println("Consolidated report created => " + output.absolutePath)
    }
}