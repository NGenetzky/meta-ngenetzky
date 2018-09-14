# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "8977748862ec148ded760756a42b15412f90a8c6"
SRC_URI = "git://github.com/ankitrgadiya/docker-c9.git"
S = "${WORKDIR}/git"

DOCKER_REPOSITORY = "meta-ngenetzky/${PN}"
DOCKER_TAG = "${PV}-${PR}"

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
    docker kill "${name}" || true
    docker rm "${name}" || true
    docker run \
        --name "${name}" \
        --volume "${TOPDIR}:/root/workspace" \
        --publish "0.0.0.0:3000:3000" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        > ${T}/log.do_server.docker_run 2>&1 &
}
