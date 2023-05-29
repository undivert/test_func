import hudson.plugins.git.GitSCM
import hudson.plugins.git.GitTool
import hudson.model.FreeStyleProject
import hudson.model.FreeStyleBuild

// Define the Git repository URL and branch
def gitRepository = 'https://github.com/example/repository.git'
def gitBranch = 'master'

// Create a new FreeStyle project
def project = new FreeStyleProject(Jenkins.instance, 'MyGitJob')

// Define the Git SCM configuration
def gitSCM = new GitSCM(gitRepository)
gitSCM.setBranches([new hudson.plugins.git.BranchSpec(gitBranch)])

// Set the Git SCM to the project
project.setScm(gitSCM)

// Clone the Git repository to the workspace
def build = project.scheduleBuild2(0).get()
