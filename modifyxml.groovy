import jenkins.model.Jenkins
import hudson.XmlFile
import hudson.model.FreeStyleProject  // Adjust the import based on your job type

def jenkins = Jenkins.getInstance()

def jobName = "myJob"  // Replace with the name of your job
def job = jenkins.getItem(jobName)

if (job != null && job instanceof FreeStyleProject) {  // Adjust the job type accordingly
    def configXml = job.getConfigFile()
    def xmlFile = configXml.getFile()

    // Read the current XML configuration
    def xml = xmlFile.text
    def updatedXml = xml.replaceAll(/<permission>hudson\.model\.Item\.Build:.*?<\/permission>/, '')

    // Update the XML configuration
    configXml.write(updatedXml)

    println("Job configuration modified successfully: $jobName")
} else {
    println("Job not found or not the expected type: $jobName")
}
