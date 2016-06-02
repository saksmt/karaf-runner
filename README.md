# karaf-runner

## Installation

```bash
mvn clean install
echo '#!'"$(which java)"' -jar -Dkaraf-runner.templates-dir="${PATH_WHERE_TEMPLATES_WOULD_LIVE}"' >> target/karaf-runner
cat target/karaf-runner.jar >> target/karaf-runner

cp target/karaf-runner $SOME_PATH_WHERE_YOU_WANT_KARAF_RUNNER_TO_BE # recommended to use /usr/local/bin
```

 - `$SOME_PATH_WHERE_YOU_WANT_KARAF_RUNNER_TO_BE` - Location where karaf-runner would be installed
 - `${PATH_WHERE_TEMPLATES_WOULD_LIVE}` - Location where karaf-runner templates would live, if not set (omitted at all) uses `/usr/local/etc/karaf`
 
Karaf image must be at `/opt/karaf-$KARAF_VERSION.0`, where `$KARAF_VERSION` is major version of karaf
 
## Template hierarchy
 
```
-- /templates_root
   |
   +-- /base - base configuration, copied to every instance
   |   |
   |   +-- /my.awesome.config.cfg
   |
   +-- /project-skel - configuration for projects (one subdir - one project)
   |   |
   |   +-- /my-project - concrete project configuration
   |       |
   |       +-- /my.another.awesome.config.cfg
   |
   +-- /skel - configuration for environments (one subdir - one environment)
   |   |
   |   +-- /dev - configuration files for dev environment
   |       |
   |       +-- /my.dev.env.related.config.cfg
   |
   +-- /project-config - contains configuration for projects in environments
       |
       +-- /my-project - contains configuration for "my-project" in environments
           |
           +-- /dev - contains configuration for "my-project" in "dev" env
               |
               +-- /config.for.my-project.started.in.dev.env.cfg
    
```

## Usage

```
  karaf-runner <install|run|get-path|shutdown> {arguments} [options]

Modules
  get-path [OPTIONS]                Get path where karaf currently installed
    Options:
      --karaf-version=<VERSION>, -k <PATH> Specifies version of karaf
                                           Defaults to "3" (/opt/karaf-3.0)

      --raw, -r                            Just show path, without user message (useful for scripts)

  install <MODE> [OPTIONS]          Installs karaf of specified version
    Options:
      --karaf-version=<VERSION>, -k <PATH>       Specifies version of karaf
                                                 Defaults to "3" (/opt/karaf-3.0)

      --templates-path=<PATH>, -T <PATH>         Specify path, where templates live
                                                 Defaults to /usr/local/etc/karaf

      --environment=<ENV>, --env=<ENV>, -e <ENV> Specifies environment of configuration, required

      --use-assembly, -A                         Tells not to use base image and search
                                                 for assembly dir instead (don't affects assembly mode)

    Arguments (MODES):
      from-features  Install karaf and use features as deployment files
      from-kars      Install karaf and use kar's as deployment files
      assembly       Don't install image and lookup for assembly dir instead
                     (semantically equal to "vanilla -A")
      vanilla        Install karaf and don't use any deployment files

  shutdown [OPTIONS]                Drop currently installed karaf
    Options:
      --karaf-version=<VERSION>, -k <PATH> Specifies version of karaf
                                               Defaults to "3" (/opt/karaf-3.0)

  drop-cache [OPTIONS]              Drop image cache (use it when you've updated any base karaf image)
   Options:
     --karaf-version=<VERSION>, -k <PATH>       Specifies version of karaf
                                                Defaults to "3" (/opt/karaf-3.0)

  run <MODE> [OPTIONS]              Install karaf and immediately run it
    Options:
      --karaf-version=<VERSION>, -k <PATH>       Specifies version of karaf
                                                 Defaults to "3" (/opt/karaf-3.0)

      --templates-path=<PATH>, -T <PATH>         Specify path, where templates live
                                                 Defaults to /usr/local/etc/karaf

      --environment=<ENV>, --env=<ENV>, -e <ENV> Specifies environment of configuration, required

      --use-assembly, -A                         Tells not to use base image and search
                                                 for assembly dir instead (don't affects assembly mode)

      --endless, -E                              Endless run of karaf (restart on exit)

      --debug, -d                                Run karaf in debug mode

      Arguments:
        from-features  Install karaf and use features as deployment files
        from-kars      Install karaf and use kar's as deployment files
        assembly       Don't install image and lookup for assembly dir instead
                       (semantically equal to "vanilla -A")
        vanilla        Install karaf and don't use any deployment files
```