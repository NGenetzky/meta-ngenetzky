# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

PV = "11.3.0"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build
SRCREV = "f2aaf51b739e12f9f182c6786cc445b512422c04"
SRC_URI = "git://gitlab.com/gitlab-org/omnibus-gitlab.git;subpath=docker;protocol=https;destsuffix=${PN}-${PV};branch=11-3-stable"

inherit docker

do_build[dirs] = "${S}"
do_build(){
    docker build \
        -t "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        .
    docker image inspect \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}
