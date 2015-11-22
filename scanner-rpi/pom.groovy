project {
    modelVersion '4.0.0'
    parent {
        groupId 'io.github.ajurasz'
        artifactId 'blog-samples'
        version '1.0-SNAPSHOT'
    }

    artifactId 'scanner-rpi'
    version '1.0-SNAPSHOT'

    properties {
        'fatjar.mainClass' 'io.github.ajurasz.scanner.RpiScanner'
    }

    dependencies {
        dependency('io.rhiot:camel-bluetooth:0.1.3-SNAPSHOT')
        dependency('io.rhiot:camel-pi4j:0.1.3-SNAPSHOT')
    }
}