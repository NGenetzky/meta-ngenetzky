# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "A boilerplate for getting up and running with Kodi on your resin-enabled rpi!"
PV = "2018.05.26"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build
SRCREV = "2a7043476a24d728a649f0f46f7a419851d22db3"
SRC_URI = "git://github.com/resin-io-playground/resin-kodi.git"
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

