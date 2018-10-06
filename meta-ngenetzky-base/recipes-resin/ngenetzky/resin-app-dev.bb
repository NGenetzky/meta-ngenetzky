# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Development of applications for Resin.io platform"
PV = "2018.06.10"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build
SRCREV = "d27130e622eeb0d5a30a873bf6482ee6c0844012"
SRC_URI = "git://github.com/NGenetzky/resin-app-dev.git"
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

