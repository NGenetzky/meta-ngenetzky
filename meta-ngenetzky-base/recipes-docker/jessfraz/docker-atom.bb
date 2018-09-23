# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

PV = "2018.09.14"
PR = "r1"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "6356143a7e76e5619510bb3f65d345cfda41ec13"
SRC_URI = "git://github.com/jessfraz/dockerfiles.git;subpath=atom;destsuffix=${PN}-${PV}"

inherit docker

atom(){
    local name="${PN}"

    # Fix for "cannot open display: unix:0"
    # https://stackoverflow.com/a/28395350
    xhost local:root

    docker run \
        --name "${PN}" \
        --rm \
        -v /tmp/.X11-unix:/tmp/.X11-unix \
        -e DISPLAY=unix$DISPLAY \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    export_func_shell('atom', d, os.path.join(workdir, 'atom.sh'), workdir)
}

do_build[dirs] = "${S}"
do_build(){
    docker build \
        -t "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        .
    docker image inspect \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}
