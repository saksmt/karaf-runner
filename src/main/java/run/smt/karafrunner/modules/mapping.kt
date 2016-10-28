package run.smt.karafrunner.modules

import run.smt.karafrunner.modules.impl.image.DropCacheModule
import run.smt.karafrunner.modules.impl.GetPathModule
import run.smt.karafrunner.modules.impl.util.AggregateModule
import run.smt.karafrunner.modules.impl.ShutdownModule
import run.smt.karafrunner.modules.impl.VersionModule
import run.smt.karafrunner.modules.impl.image.UpdateImageModule
import run.smt.karafrunner.modules.impl.installation.*
import run.smt.karafrunner.modules.impl.run.*
import run.smt.karafrunner.modules.impl.util.TextProvidingModule

val imageModules = mapOf(
        "drop-cache" to DropCacheModule(),
        "update" to UpdateImageModule(),
        "install" to UpdateImageModule()
)

val installationModulesMapping = mapOf(
        "from-features" to InstallFromFeaturesModule(),
        "from-kars" to InstallFromKarsModule(),
        "from-config" to InstallFromConfigModule(),
        "assembly" to InstallFromAssemblyModule(),
        "vanilla" to InstallVanillaModule()
)

val runModulesMapping = mapOf(
        "from-features" to RunFromFeaturesModule(),
        "from-kars" to RunFromKarsModule(),
        "from-config" to RunFromConfigModule(),
        "assembly" to RunFromAssemblyModule(),
        "vanilla" to RunVanillaModule()
)

val mainModulesMapping = mapOf(
        "help" to TextProvidingModule(Thread.currentThread().contextClassLoader.getResource("help.txt").readText()),
        "get-path" to GetPathModule(),
        "shutdown" to ShutdownModule(),
        "version" to VersionModule(),
        "install" to AggregateModule(installationModulesMapping, "vanilla"),
        "run" to AggregateModule(runModulesMapping, "vanilla"),
        "image" to AggregateModule(imageModules)
)
