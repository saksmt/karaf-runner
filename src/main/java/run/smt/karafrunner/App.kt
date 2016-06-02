package run.smt.karafrunner

import run.smt.karafrunner.modules.impl.util.AggregateModule
import run.smt.karafrunner.modules.mainModulesMapping

fun main(args: Array<String>) {
    AggregateModule(mainModulesMapping).run(args.asList())
}
