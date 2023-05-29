import hudson.plugins.git.GitSCM
import hudson.plugins.git.GitTool
import hudson.model.FreeStyleProject
import hudson.model.FreeStyleBuild
import jenkins.model.Jenkins
import com.cloudbees.plugins.credentials.CredentialsProvider
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.domains.DomainCredentials

// Define the Git repository URL and branch
def gitRepository = 'https://github.com/example/repository.git'
def gitBranch = 'master'

// Define the credentials ID configured in Jenkins
def credentialsId = 'your-credentials-id'

// Create a new FreeStyle project
def project = new FreeStyleProject(Jenkins.instance, 'MyGitJob')

// Define the Git SCM configuration
def gitSCM = new GitSCM(gitRepository)
gitSCM.setBranches([new hudson.plugins.git.BranchSpec(gitBranch)])

// Get the credentials object
def credentials = CredentialsProvider.findCredentialById(credentialsId, Domain.global())

// Set the credentials to the Git SCM
gitSCM.getUserRemoteConfigs()[0].credentialsId = credentials.getId()

// Set the Git SCM to the project
project.setScm(gitSCM)

// Clone the Git repository to the workspace
def build = project.scheduleBuild2(0).get()
