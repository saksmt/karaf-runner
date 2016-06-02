package run.smt.karafrunner.modules.impl.util

import run.smt.karafrunner.modules.api.AbstractModule

class TextProvidingModule(private val text: String): AbstractModule() {
    override fun doRun() {
        println(text)
    }
}