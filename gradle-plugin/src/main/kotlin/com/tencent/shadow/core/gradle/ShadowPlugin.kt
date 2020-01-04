package com.tencent.shadow.core.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.sql.DriverManager.println

/**
 * create by yueyue_projects on 2019/12/30
 */

class ShadowPlugin : Plugin<Project> {
    override fun apply(p0: Project) {
        println("ShadowPlugin --> test")
    }
}