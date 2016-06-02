package run.smt.karafrunner.modules

import run.smt.karafrunner.modules.impl.DropCacheModule
import run.smt.karafrunner.modules.impl.GetPathModule
import run.smt.karafrunner.modules.impl.installation.InstallFromFeaturesModule
import run.smt.karafrunner.modules.impl.util.AggregateModule
import run.smt.karafrunner.modules.impl.ShutdownModule
import run.smt.karafrunner.modules.impl.installation.InstallFromAssemblyModule
import run.smt.karafrunner.modules.impl.util.TextProvidingModule
import run.smt.karafrunner.modules.impl.installation.InstallFromKarsModule
import run.smt.karafrunner.modules.impl.installation.InstallVanillaModule
import run.smt.karafrunner.modules.impl.run.RunFromAssemblyModule
import run.smt.karafrunner.modules.impl.run.RunFromFeaturesModule
import run.smt.karafrunner.modules.impl.run.RunFromKarsModule
import run.smt.karafrunner.modules.impl.run.RunVanillaModule

val installationModulesMapping = mapOf(
        "from-features" to InstallFromFeaturesModule(),
        "from-kars" to InstallFromKarsModule(),
        "assembly" to InstallFromAssemblyModule(),
        "vanilla" to InstallVanillaModule()
)

val runModulesMapping = mapOf(
        "from-features" to RunFromFeaturesModule(),
        "from-kars" to RunFromKarsModule(),
        "assembly" to RunFromAssemblyModule(),
        "vanilla" to RunVanillaModule()
)

val mainModulesMapping = mapOf(
        "help" to TextProvidingModule(Thread.currentThread().contextClassLoader.getResource("help.txt").readText()),
        "get-path" to GetPathModule(),
        "shutdown" to ShutdownModule(),
        "install" to AggregateModule(installationModulesMapping, "vanilla"),
        "drop-cache" to  DropCacheModule(),
        "run" to AggregateModule(runModulesMapping, "vanilla")
)
