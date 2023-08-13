import groovy.xml.XmlParser

def validateXml(xmlContent) {
    try {
        new XmlParser().parseText(xmlContent)
        return true // XML is valid
    } catch (Exception e) {
        return false // XML is invalid or malformed
    }
}

def jobNames = ["job1", "job2", "job3"] // List of job names to process

def workspace = new File(System.getProperty("user.home")).toString() // Replace with the desired workspace path
def jenkinsUrl = "http://jenkins-server-url" // Replace with your Jenkins server URL
def cliJarPath = "/path/to/jenkins-cli.jar" // Replace with the path to jenkins-cli.jar on your system

def cli = new CLI(new URL(jenkinsUrl), null, null)

jobNames.each { jobName ->
    def job = Jenkins.instance.getItem(jobName)
    if (job) {
        def configFile = new File("${workspace}/${jobName}.xml")

        // Grab the job configuration and write it to the workspace
        configFile.text = job.getConfigFile().asString()

        // Validate the XML content before proceeding
        if (!validateXml(configFile.text)) {
            println "XML content for ${jobName} is malformed or invalid. Skipping."
            return
        }

        // ... (Rest of your script)
    } else {
        println "Job ${jobName} not found."
    }
}

cli.close()
