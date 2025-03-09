plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.wzh.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        resourceConfigurations += listOf("en", "zh", "zh-rCN", "zh-rHK", "zh-rTW")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api(libs.androidx.datastore.preferences)

    api(libs.recyclerview)
    api(libs.lifecycle.extensions)
    api(libs.lifecycle.livedata.ktx)
    api(libs.lifecycle.runtime.ktx)
    api(libs.lifecycle.viewmodel.ktx)
    api(libs.material)

    api(libs.lottie)

    api(libs.refresh.layout.kernel)      // 核心必须依赖
    api(libs.refresh.header.classics)    // 经典刷新头
// api 'com.scwang.smart:refresh-header-material:2.0.1'    // 谷歌刷新头
    api(libs.refresh.footer.classics)   // 经典加载

    api(libs.fragment.ktx)

    // 使用 Version Catalog 中的 Glide 依赖
    api(libs.glide)
    annotationProcessor(libs.glide.compiler)
}