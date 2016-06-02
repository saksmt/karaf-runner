package run.smt.karafrunner.modules.impl

import org.kohsuke.args4j.Option
import run.smt.karafrunner.modules.api.AbstractModule

abstract class BaseModule : AbstractModule() {
    @Option(name = "--templates-path", aliases = arrayOf("-T"), required = false)
    protected var templatesPath = System.getProperty("karaf-runner.templates-dir", "/usr/local/etc/karaf/")
}