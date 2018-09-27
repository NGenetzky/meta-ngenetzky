# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "A demo of resin.io multicontainer on Raspberry Pi3 with PiTFT LCDs"
PV = "2018.08.10"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build
SRCREV = "90862d2d798ffa68bcfad52e5108e8638ebf7261"
SRC_URI = "git://github.com/resin-io-projects/multicontainer-demo-rpi3.git"
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

