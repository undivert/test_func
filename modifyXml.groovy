import hudson.model.*
import hudson.cli.CLI

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

        // Create a backup of the original job_name.xml
        def backupFile = new File("${workspace}/${jobName}.original.xml")
        backupFile.text = configFile.text

        // Modify the job configuration file
        def modifiedConfig = configFile.text.replaceAll(/<Permission>hudson.model.Item.Configure:.*?<\/Permission>/, "")

        // Save the modified configuration back to the file
        configFile.text = modifiedConfig

        // Load the modified configuration using jenkins-cli.jar
        def loadCommand = ["java", "-jar", cliJarPath, "-s", jenkinsUrl, "update-job", jobName, "<", configFile.toString()]
        def process = loadCommand.execute()
        process.waitFor()

        println "Configuration for ${jobName} modified and loaded successfully."
    } else {
        println "Job ${jobName} not found."
    }
}

cli.close()
