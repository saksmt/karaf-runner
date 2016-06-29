# karaf-runner

Cross-platform (actually not tested on M$ Windows) utility tool for running Apache Karaf

## Features

 - Locate and pre-install features/kars
 - Handle cross-project dependencies
 - Copy configuration based on environment and project
 - Handling of multiple Karaf images/version/assemblies
 - Locating and running custom Karaf assemblies built with maven karaf-assembly plugin
 
## Quick usage example

Simple as pie - just run the following:

```bash
karaf-runner run from-kars --environment dev
# or if you want to run your project from features:
karaf-runner run from-features
# or maybe you want to run custom Karaf assembly?
karaf-runner run from-assembly
```

For more see sections below

## Installation

### Unix-like systems

#### 1

```bash
sudo ./install [--installation-path /usr/local/bin] [--configuration-file /usr/local/etc/karaf-runner/config]
```

#### 1.1 (optional)

You may want to configure where your configuration templates and images would live

```
# /usr/loca/etc/karaf-runner/config

templatesPath += /usr/local/etc/karaf-runner
imagesPath += /opt/karaf-runner/images
```

#### 2

```bash
sudo karaf-runner image install /path/to/directory/with/karaf-assembly
```

Also you can simply `cd` into directory with assembly and run command without path.
(Actually path needn't to be direct parent of assembly, any parent in hierarchy is allowed,
ex. /path/to is also accepted)

#### 3

It's time to run it!

### Windows

Windows users MUST SUFFER :smiling_imp:

OK, it's just a joke: just build it with maven and at every execution of karaf-runner
pass `-Dkaraf-runner.configurationFile=PATH_TO_YOUR_CONFIGURATION`, every thing else is
backward compatible with **Unix-like**-section, just replace prefix according to following table:

| Unix           | Windows         |
|----------------|-----------------|
| /usr/local/etc | %HOME%\\.config |
| /opt           | %HOME%\\.opt    |

Also you can use configuration file to place your templates and images where you want

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
Configuration consists of 3 parts:

 1. Projects description - contains list of project names, all of them would be used
 2. Dependencies description - contains paths of projects on which current depends
 3. Image - you can specify image to use instead of "default" as default one 
 
Karaf-runner would scan also dependent configurations, so if your project (A) depends on project B, depending on project C
with names: C and D, you'll have project names as following: C, D, B, A

Dependency paths may be both absolute and relative on your choice.

### Format

Format is much like qmake's .pro files, except of the only operator supported is `+=` ^^

Keys:

 - `projects`
 - `project` - alias for `projects`
 - `dependencies`
 - `dependency` - alias for `dependencies`
 - `images`
 - `image` - alias for `images`
 
#### Example

```
image = caterpillar
projects += pim
projects += pom
dependencies += ../ssm
```

## Usage

```
Usage
  karaf-runner <install|run|image|get-path|shutdown|help> {arguments} [options]

Modules
  help                              Show this help message
  get-path [OPTIONS]                Get path where karaf currently installed
    Options:
      --image=<IMAGE_NAME>, -i <IMAGE_NAME>   Specifies image name
                                              Defaults to "default" (/opt/karaf-runner/images/$IMAGE_NAME)

      --raw, -r                               Just show path, without user message (useful for scripts)

  install <MODE> [OPTIONS]          Installs karaf of specified version
    Options:
      --image=<IMAGE_NAME>, -i <IMAGE_NAME>      Specifies image name
                                                 Defaults to "default" (/opt/karaf-runner/images/$IMAGE_NAME)

      --project-name=<PROJECT_NAME>,             Explicitly specify project names, repeat option for
      -p <PROJECT_NAME>                          setting more than one project name

      -P                                         Disable ask for project name and don't copy project templates

      --templates-path=<PATH>, -T <PATH>         Specify path, where templates live
                                                 Defaults to /usr/local/etc/karaf

      --environment=<ENV>, --env=<ENV>, -e <ENV> Specifies environment of configuration, if not set
                                                 doesn't copy environment related templates

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
      --image=<IMAGE_NAME>, -i <IMAGE_NAME>    Specifies image name
                                               Defaults to "default" (/opt/karaf-runner/images/$IMAGE_NAME)

  image <MODE> [OPTIONS]            Manipulate image
    Options:
      --image=<IMAGE_NAME>, -i <IMAGE_NAME>   Specifies image name
                                              Defaults to "default" (/opt/karaf-runner/images/$IMAGE_NAME)

    Arguments (MODES):
      drop-cache           Drop image cache (use it when you've updated any base karaf image)
                           If image was updated with karaf-runner it is called automatically
      update [IMAGE_PATH]  Replace current image with specified one (default path is $PWD)
                           Path may be absolute path to image (assembly dir) or just one of
                           the parent paths
      install [IMAGE_PATH] Alias for "update"

  run <MODE> [OPTIONS]              Install karaf and immediately run it
    Options:
      --image=<IMAGE_NAME>, -i <IMAGE_NAME>      Specifies image name
                                                 Defaults to "default" (/opt/karaf-runner/images/$IMAGE_NAME)

      --project-name=<PROJECT_NAME>,             Explicitly specify project names, repeat option for
      -p <PROJECT_NAME>                          setting more than one project name

      -P                                         Disable ask for project name and don't copy project templates

      --templates-path=<PATH>, -T <PATH>         Specify path, where templates live
                                                 Defaults to /usr/local/etc/karaf

      --environment=<ENV>, --env=<ENV>, -e <ENV> Specifies environment of configuration, if not set
                                                 doesn't copy environment related templates

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

## License

All source code is licensed under [MIT license](./LICENSE)