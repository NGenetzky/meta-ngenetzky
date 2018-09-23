# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

PV = "2018.04.27"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

DOCKER_NAME ?= "${PN}"

SRCREV = "d2bb8fbcf30501b7b0fc971085137f521efb6435"
SRC_URI = "\
    git://github.com/jessfraz/dockerfiles.git;subpath=gitserver;destsuffix=${PN}-${PV} \
    file://authorized_keys \
"

inherit docker

console(){
    docker exec -it "${DOCKER_NAME}" /bin/bash
}

server(){
    local homedir="${@docker_volume_path(d,'gitserver')}"
    docker run -it --rm \
        --name "${DOCKER_NAME}" \
        -e DEBUG=true \
        -e "PUBKEY=$(cat ${WORKDIR}/authorized_keys)" \
        -v "${homedir}:/home/git/" \
        --publish "10022:22" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    export_func_shell('console', d, os.path.join(workdir, 'console.sh'), workdir)
    export_func_shell('server', d, os.path.join(workdir, 'server.sh'), workdir)
}

do_build[dirs] = "${S}"
do_build(){
    docker build \
        -t "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        .
    docker image inspect \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}
