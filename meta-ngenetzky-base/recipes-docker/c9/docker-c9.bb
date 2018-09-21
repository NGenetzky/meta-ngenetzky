# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "8977748862ec148ded760756a42b15412f90a8c6"
SRC_URI = "git://github.com/ankitrgadiya/docker-c9.git"
S = "${WORKDIR}/git"

inherit docker

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    export_func_shell('do_server', d, os.path.join(workdir, 'do_server.sh'), workdir)
}

do_build[dirs] = "${S}"
do_build(){
    docker build \
        -t "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        .
    docker image inspect \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}

server[stamp] = "0"
addtask do_server after do_build
do_server(){
    local name="${PN}"
    docker run \
        -d \
        --name "${name}" \
        --volume "${TOPDIR}:/root/workspace" \
        --publish "0.0.0.0:3000:3000" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}
