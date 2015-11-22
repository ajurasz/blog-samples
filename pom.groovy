project {
    modelVersion '4.0.0'
    groupId 'io.github.ajurasz'
    artifactId 'blog-samples'
    packaging 'pom'
    version '1.0-SNAPSHOT'

    modules {
        module('scanner-pc')
        module('scanner-rpi')
    }

    properties {
        'fatjar.mainClass' 'com.example.MainClass'
    }

    dependencies {
        dependency('org.slf4j:slf4j-api:1.7.12')
        dependency('org.slf4j:slf4j-log4j12:1.7.12')
        dependency('log4j:log4j:1.2.17')
    }

    build {
        plugins {
            plugin('io.takari.polyglot:polyglot-translate-plugin:0.1.14') {
                configuration {
                    input 'pom.groovy'
                    output 'pom.xml'
                }
            }

            plugin('org.apache.maven.plugins:maven-compiler-plugin') {
                configuration {
                    source '1.8'
                    target '1.8'
                }
            }

            plugin('org.apache.maven.plugins:maven-shade-plugin:2.4.2') {
                executions {
                    execution {
                        phase 'package'
                        goals {
                            goal 'shade'
                        }
                        configuration {
                            transformers {
                                transformer(implementation: 'org.apache.maven.plugins.shade.resource.ManifestResourceTransformer') {
                                    mainClass '${fatjar.mainClass}'
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}