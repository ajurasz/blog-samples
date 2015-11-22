project {
    modelVersion '4.0.0'
    parent {
        groupId 'io.github.ajurasz'
        artifactId 'blog-samples'
        version '1.0-SNAPSHOT'
    }

    artifactId 'scanner-pc'
    version '1.0-SNAPSHOT'

    properties {
        'fatjar.mainClass' 'io.github.ajurasz.scanner.PcScanner'
    }

    dependencies {
        dependency('org.apache.camel:camel-core:2.16.0')
        dependency('org.apache.camel:camel-netty4-http:2.16.0')
        dependency('org.apache.camel:camel-jackson:2.16.0')
        dependency('io.rhiot:camel-bluetooth:0.1.3-SNAPSHOT')
    }
}