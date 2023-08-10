import jenkins.model.Jenkins
import hudson.XmlFile

def jenkins = Jenkins.getInstance()

def jobName = "myJob"  // Replace with the name of your job
def job = jenkins.getItem(jobName)

if (job != null) {
    def configXml = job.getConfigFile().file
    def xmlFile = new XmlFile(configXml)
    
    def xml = xmlFile.asString()
    def xmlSlurper = new XmlSlurper().parseText(xml)
    
    // Remove the specific permission element
    xmlSlurper.permissions.permission.find { it.text() == 'hudson.model.Item.Build:xxx' }.replaceNode {}
    
    // Save the modified XML
    xmlFile.write(xmlSlurper)
    
    println("Job configuration modified successfully: $jobName")
} else {
    println("Job not found: $jobName")
}
