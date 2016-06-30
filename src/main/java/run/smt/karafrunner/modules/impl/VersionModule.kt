package run.smt.karafrunner.modules.impl

import run.smt.karafrunner.modules.api.Module
import run.smt.karafrunner.Version.VERSION

class VersionModule : Module {
    override fun run(arguments: List<String>) {
        println("karaf-runner v$VERSION")
        println("Copyright (c) 2016 Kirill Saksin")
        println("Licensed under The MIT License")
    }
}

