package com.ssports.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        AppExtension appExtension = project.extensions.getByType(AppExtension)
        appExtension.registerTransform(new MyTransform(project))
    }
}