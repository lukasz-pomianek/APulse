# Publishing APulse

This guide explains how to publish APulse to various repositories.

## Publishing Options

### 1. GitHub Packages (Recommended for Private)

**Setup GitHub Packages in `build.gradle.kts`:**

```kotlin
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/lukasz-pomianek/APulse")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}
```

**Publish:**
```bash
./gradlew publishReleasePublicationToGitHubPackagesRepository
```

**Usage:**
```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/lukasz-pomianek/APulse")
        credentials {
            username = project.findProperty("gpr.user") as String?
            password = project.findProperty("gpr.key") as String?
        }
    }
}

dependencies {
    implementation("com.apulse:apulse-core:1.0.0")
    debugImplementation("com.apulse:apulse:1.0.0")
}
```

### 2. JitPack (Recommended for Open Source)

**No configuration needed!** JitPack builds directly from GitHub tags.

**Tag your release:**
```bash
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

**Usage:**
```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.lukasz-pomianek.apulse:apulse-core:1.0.0")
    debugImplementation("com.github.lukasz-pomianek.apulse:app:1.0.0")
}
```

### 3. Maven Central (For Wide Distribution)

**Setup Sonatype account and configure signing:**

```kotlin
// In root build.gradle.kts
plugins {
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}
```

**In module `build.gradle.kts`:**
```kotlin
plugins {
    id("maven-publish")
    id("signing")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            // ... existing configuration ...
            
            pom {
                // Complete POM configuration required for Maven Central
                name.set("APulse Core")
                description.set("Network debugging library for Android")
                url.set("https://github.com/lukasz-pomianek/APulse")
                
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                
                developers {
                    developer {
                        id.set("lukasz-pomianek")
                        name.set("Your Name")
                        email.set("your.email@example.com")
                    }
                }
                
                scm {
                    connection.set("scm:git:git://github.com/lukasz-pomianek/APulse.git")
                    developerConnection.set("scm:git:ssh://github.com/lukasz-pomianek/APulse.git")
                    url.set("https://github.com/lukasz-pomianek/APulse")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["release"])
}
```

## Release Process

### 1. Prepare Release

```bash
# Update version in all build.gradle.kts files
# Update CHANGELOG.md
# Update README.md with new version
```

### 2. Build and Test

```bash
# Build all modules
./gradlew clean assembleRelease

# Run tests
./gradlew test

# Test sample app
./gradlew :sample:installDebug
```

### 3. Publish

```bash
# For GitHub Packages
./gradlew publishReleasePublicationToGitHubPackagesRepository

# For Maven Central (after Sonatype setup)
./gradlew publishToSonatype closeAndReleaseSonatypeStagingRepository

# For JitPack (just tag)
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

### 4. Create GitHub Release

```bash
# Create release on GitHub with:
# - Tag: v1.0.0
# - Title: APulse v1.0.0
# - Description: Changelog for this version
# - Attach APK files from sample app
```

## Version Management

### Semantic Versioning

- **Major** (1.0.0): Breaking API changes
- **Minor** (1.1.0): New features, backward compatible
- **Patch** (1.0.1): Bug fixes

### Version Configuration

**In `gradle.properties`:**
```properties
VERSION_NAME=1.0.0
VERSION_CODE=1

GROUP=com.apulse
POM_ARTIFACT_ID=apulse-core

POM_NAME=APulse Core
POM_DESCRIPTION=Network debugging library for Android
POM_INCEPTION_YEAR=2024
POM_URL=https://github.com/lukasz-pomianek/APulse

POM_LICENSE_NAME=MIT License
POM_LICENSE_URL=https://opensource.org/licenses/MIT

POM_DEVELOPER_ID=lukasz-pomianek
POM_DEVELOPER_NAME=Łukasz Pomianek
POM_DEVELOPER_URL=https://github.com/lukasz-pomianek

POM_SCM_URL=https://github.com/lukasz-pomianek/APulse
POM_SCM_CONNECTION=scm:git:git://github.com/lukasz-pomianek/APulse.git
POM_SCM_DEV_CONNECTION=scm:git:ssh://git@github.com/lukasz-pomianek/APulse.git
```

## Distribution Strategy

### Recommended Approach

1. **Open Source**: Use JitPack for easy distribution
2. **Enterprise**: Use GitHub Packages or private Maven repository  
3. **Wide Adoption**: Publish to Maven Central

### Multi-Module Publishing

```bash
# Publish both core and full UI modules
./gradlew :apulse-core:publishReleasePublicationToGitHubPackagesRepository
./gradlew :app:publishReleasePublicationToGitHubPackagesRepository
```

## Documentation Updates

### Update Integration Docs

When publishing, update:

1. **README.md**: Installation instructions with correct version
2. **INTEGRATION.md**: Usage examples with current API
3. **CHANGELOG.md**: What's new in this version
4. **API documentation**: If API changes

### Example Documentation Update

```markdown
## Installation

```kotlin
dependencies {
    implementation("com.apulse:apulse-core:1.0.0")  // ← Update version
    debugImplementation("com.apulse:apulse:1.0.0")  // ← Update version
}
```

## Verification

### Test Installation

Create a test project and verify:

1. **Dependencies resolve**: `./gradlew dependencies`
2. **Code compiles**: `./gradlew assembleDebug`
3. **APulse works**: Run app, make network request, launch APulse UI
4. **Proguard compatible**: `./gradlew assembleRelease`

### Checklist Before Publishing

- [ ] Version numbers updated in all `build.gradle.kts` files
- [ ] CHANGELOG.md updated
- [ ] README.md has correct installation instructions  
- [ ] All tests pass
- [ ] Sample app builds and runs
- [ ] Documentation is current
- [ ] Git tag created
- [ ] GitHub release created with APK attachments

## Troubleshooting

### Common Issues

**"Could not resolve dependency"**
- Check repository URL and credentials
- Verify version exists
- Check network/proxy settings

**"POM validation failed"**
- Ensure all required POM fields are filled
- Check license and developer information

**"Signing failed"**
- Verify GPG key setup for Maven Central
- Check signing credentials

### Debug Publishing

```bash
# Verbose output
./gradlew publishReleasePublicationToGitHubPackagesRepository --info

# Check what will be published
./gradlew publishToMavenLocal
ls ~/.m2/repository/com/APulse/
```