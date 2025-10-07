plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
}

android {
    namespace = "com.apulse.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    // Android Core (minimal dependencies for core module)
    implementation("androidx.core:core-ktx:1.12.0")
    
    // Networking - the only required dependency for the API
    api("com.squareup.okhttp3:okhttp:4.12.0")
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}


publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.apulse"
            artifactId = "apulse-core"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
            
            pom {
                name.set("APulse Core")
                description.set("Lightweight core library for APulse network debugging tool")
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
                        name.set("Łukasz Pomianek")
                        url.set("https://github.com/lukasz-pomianek")
                    }
                }
                
                scm {
                    connection.set("scm:git:git://github.com/lukasz-pomianek/APulse.git")
                    developerConnection.set("scm:git:ssh://git@github.com/lukasz-pomianek/APulse.git")
                    url.set("https://github.com/lukasz-pomianek/APulse")
                }
            }
        }
    }
}