# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "2459fd0772ea8461393253ff4bd5e377b9004680"
SRC_URI = "git://github.com/crops/yocto-dockerfiles.git;subpath=;destsuffix=${PN}-${PV}"

inherit docker

do_build[dirs] = "${S}"
do_build(){
    cp -T \
        "${S}/dockerfiles/ubuntu/ubuntu-16.04/ubuntu-16.04-base/Dockerfile" \
        "${S}/Dockerfile"

    docker build \
        -t "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        .
    docker image inspect \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}
