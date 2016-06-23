package run.smt.karafrunner.modules.impl.util

import run.smt.karafrunner.modules.api.Module

class TextProvidingModule(private val text: String): Module {
    override fun run(arguments: List<String>) {
        println(text)
    }
}