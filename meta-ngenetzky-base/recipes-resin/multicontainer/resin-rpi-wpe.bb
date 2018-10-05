# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Fullscreen WebKit browser with hardware accelerated CSS, WebGL, and HTML5 video for the RaspberryPi 3."
PV = "2018.05.21"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build
SRCREV = "10173157559a55ab34ba981efd4a93980ac94d93"
SRC_URI = "git://github.com/resin-io-projects/resin-wpe.git"
S = "${WORKDIR}/git"

RESIN_USER = "gh_ngenetzky"
RESIN_APP = "rpi3app2"

do_build[dirs] = "${S}"
do_build() {
    local remote="resin"
    if ! git config "remote.${remote}.url" > /dev/null; then
        git remote add "${remote}" \
            "${RESIN_USER}@git.resin.io:${RESIN_USER}/${RESIN_APP}.git"
    fi
    git push --force \
        "${remote}" master
}

