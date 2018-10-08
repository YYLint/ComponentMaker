package com.yy.mobile

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.FeatureExtension
import com.android.build.gradle.FeaturePlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import groovy.util.XmlSlurper
import org.gradle.api.DomainObjectSet
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.reflect.KClass

class ButterKnifePlugin : Plugin<Project> {

    var outputDir: File? = null
    var rPackage = ""

    override fun apply(project: Project) {
        outputDir = project.buildDir.resolve("generated/source/Rs")
        processProtection(project, outputDir!!.absolutePath, "Rs.java")
        project.plugins.all {
            when (it) {
                is FeaturePlugin -> {
                    project.extensions[FeatureExtension::class].run {
                        configureRsGeneration(project, featureVariants)
                        configureRsGeneration(project, libraryVariants)
                    }
                }
                is LibraryPlugin -> {
                    project.extensions[LibraryExtension::class].run {
                        configureRsGeneration(project, libraryVariants)
                    }
                }
                is AppPlugin -> {
                    project.extensions[AppExtension::class].run {
                        configureRsGeneration(project, applicationVariants)
                    }
                }
            }
        }

//        project.afterEvaluate {

//        }
    }

    // PaRse the variant's main manifest file in order to get the package id which is used to create
    // R.java in the right place.
    private fun getPackageName(variant: BaseVariant): String {
        val slurper = XmlSlurper(false, false)
        val list = variant.sourceSets.map { it.manifestFile }

        // According to the documentation, the earlier files in the list are meant to be overridden by the later ones.
        // So the fiRst file in the sourceSets list should be main.
        val result = slurper.parse(list[0])
        return result.getProperty("@package").toString()
    }

    private fun configureRsGeneration(project: Project, variants: DomainObjectSet<out BaseVariant>) {
        variants.all { variant ->
            val useAndroidX = (project.findProperty("android.useAndroidX") as String?)?.toBoolean()
                ?: false


            val task = project.tasks.create("generate${variant.name.capitalize()}Rs")
            task.inputs.property("useAndroidX", useAndroidX)
            task.outputs.dir(outputDir)
            variant.registerJavaGeneratingTask(task, outputDir)
            rPackage = getPackageName(variant)
            val once = AtomicBoolean()
            variant.outputs.all { output ->
                val processResources = output.processResources
                task.dependsOn(processResources)

                // Though there might be multiple outputs, their R files are all the same. Thus, we only
                // need to configure the task once with the R.java input and action.
                if (once.compareAndSet(false, true)) {
                    val pathToR = rPackage.replace('.', File.separatorChar)
                    val rFile = processResources.sourceOutputDir.resolve(pathToR).resolve("R.java")
                    task.apply {
                        inputs.file(rFile)
                        doLast {
                            FinalRClassBuilder.brewJava(rFile, outputDir, rPackage, "Rs", !useAndroidX)
                            recordPackagePath(rPackage, outputDir!!, "packageRecord.txt")
                        }
                    }
                }
            }


        }
    }

    private fun recordPackagePath(packagePath: String, dir: File, fileName: String) {
        try {
            if (dir.isDirectory) {
                val aimFile = File(dir, fileName)
                if (!aimFile.exists()) {
                    aimFile.createNewFile()

                    BufferedWriter(FileWriter(aimFile)).use { output ->
                        output.write(packagePath)
                        output.flush()
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private operator fun <T : Any> ExtensionContainer.get(type: KClass<T>): T {
        return getByType(type.java)!!
    }

    private fun processProtection(project: Project, outputDir: String, fileName: String) {
        if ((project.plugins.hasPlugin(AppPlugin::class.java) || project.plugins.hasPlugin(LibraryPlugin::class.java)) && project.hasProperty("android")) {
            val android: BaseExtension? = project.property("android") as? BaseExtension
            android?.defaultConfig {
                it.javaCompileOptions {
                    it.annotationProcessorOptions.argument("R_FILE_BEFORE_PATH", outputDir)
                    it.annotationProcessorOptions.argument("R_FILE_AFTER_PATH", fileName)
                }
            }
        }
    }
}
