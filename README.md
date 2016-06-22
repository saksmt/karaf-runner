# karaf-runner

## Installation

```bash
SOME_PATH_WHERE_YOU_WANT_KARAF_RUNNER_TO_BE=/usr/local/bin
PATH_WHERE_TEMPLATES_WOULD_LIVE=/usr/local/etc/karaf
mvn clean install
echo '#!'"$(which java)"' -jar -Dkaraf-runner.templates-dir="${PATH_WHERE_TEMPLATES_WOULD_LIVE}"' >> target/karaf-runner
cat target/karaf-runner.jar >> target/karaf-runner
chmod +x target/karaf-runner

cp target/karaf-runner $SOME_PATH_WHERE_YOU_WANT_KARAF_RUNNER_TO_BE # recommended to use /usr/local/bin
```

 - `$SOME_PATH_WHERE_YOU_WANT_KARAF_RUNNER_TO_BE` - Location where karaf-runner would be installed
 - `${PATH_WHERE_TEMPLATES_WOULD_LIVE}` - Location where karaf-runner templates would live, if not set (omitted at all) uses `/usr/local/etc/karaf`
 
That was just building project, making jar to be executable and passing default property to each execution of jar.

Karaf image (assembly) must be located at `/opt/karaf-$KARAF_VERSION.0`, where `$KARAF_VERSION` is major version of karaf,
default version for karaf is 3
 
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

## Configuration

Every project dir may contain `.karaf-runner.project` file with karaf-runner configuration for that project.
Configuration consists of 2 parts:

 1. Projects description - contains list of project names, all of them would be used
 2. Dependencies description - contains paths of projects on which current depends
 
Karaf-runner would scan also dependent configurations, so if your project (A) depends on project B, depending on project C
with names: C and D, you'll have project names as following: C, D, B, A

Dependency paths may be both absolute and relative on your choice.

### Format

Format is much like qmake's .pro files, except of the only operator supported is `+=` ^^

Keys:

 - `projects`
 - `dependencies`
 
#### Example

```
projects += pim
projects += pom
dependencies += ../ssm
```

## Usage

```
  karaf-runner <install|run|image|get-path|shutdown> {arguments} [options]

Modules
  get-path [OPTIONS]                Get path where karaf currently installed
    Options:
      --karaf-version=<VERSION>, -k <VERSION> Specifies version of karaf
                                              Defaults to "3" (/opt/karaf-3.0)

      --raw, -r                               Just show path, without user message (useful for scripts)

  install <MODE> [OPTIONS]          Installs karaf of specified version
    Options:
      --karaf-version=<VERSION>, -k <VERSION>    Specifies version of karaf
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
      --karaf-version=<VERSION>, -k <VERSION>  Specifies version of karaf
                                               Defaults to "3" (/opt/karaf-3.0)

  image <MODE> [OPTIONS]            Manipulate image
    Options:
      --karaf-version=<VERSION>, -k <VERISON> Specifies version of karaf
                                              Defaults to "3" (/opt/karaf-3.0)

    Arguments (MODES):
      drop-cache           Drop image cache (use it when you've updated any base karaf image)
                           If image was updated with karaf-runner it is called automatically
      update [IMAGE_PATH]  Replace current image with specified one (default path is $PWD)
                           Path may be absolute path to image (assembly dir) or just one of
                           the parent paths

  run <MODE> [OPTIONS]              Install karaf and immediately run it
    Options:
      --karaf-version=<VERSION>, -k <VERSION>    Specifies version of karaf
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