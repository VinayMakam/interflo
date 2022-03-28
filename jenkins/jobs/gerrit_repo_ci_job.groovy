multibranchPipelineJob('gerrit-jenkins-integration-CI') {
    description('Jenkins and Gerrit Integration')
    branchSources {
        branchSource {
            source {
                gerrit {
                    id('gerrit-ci-branch-source')
                    credentialsId('gerrit-jenkins-user')
                    remote('http://gerrit:8080/a/gerrit_project_name')
                    insecureHttps(false)
                    traits {
                        changeDiscoveryTrait {
                            queryString('')
                        }
                        headRegexFilter {
                            regex(/\d+\/\d+\/\d+/) // regex to build only changes refs, example origin/01/1/1
                        }
                        refSpecsSCMSourceTrait {
                            templates {
                                refSpecTemplate {
                                    value('+refs/changes/*:refs/remotes/@{remote}/changes/*') // fetch only changes refs
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    factory {
        workflowBranchProjectFactory {
            scriptPath('.Jenkinsfile') // hidden jenkins file
        }
    }
}