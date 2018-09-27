# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Dockerbake Ubuntu 16.04 Container"

inherit bb_fetcher
addtask do_unpack before do_build

SRC_URI = "\
    file://Dockerfile;subdir=${PN}-${PV} \
"

inherit docker

build_0(){
    apt-get update && apt-get install -y \
      cowsay \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*
}

DOCKERBAKE_FUNCTIONS = "\
    build_0 \
"

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
do_build_shell_scripts[dirs] = "${B}"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    keys_for_export = (key for key in d.keys() if not key.startswith("__") and not d.getVarFlag(key, "func", False))
    for key in keys_for_export:
        d.setVarFlag(key,'export', False)

    workdir = d.getVar('WORKDIR', expand=True)
    builddir = d.getVar('B', expand=True)

    for func in d.getVar('DOCKERBAKE_FUNCTIONS','').split():
        fpath = os.path.join(builddir, func + '.sh')
        export_func_shell(func, d, fpath)
        os.chmod(fpath, 0o775)
}

do_build[dirs] = "${B}"
do_build(){
    docker build \
        -t "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        .
    docker image inspect \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}
