# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Personal Bitbake Layer"
PV = "2018.03.03"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "347182b6760cec3c54325928337ecd0ab766afe7"
SRC_URI = "repo://github.com/NGenetzky/${PN}.git;protocol=git;branch=master"
S = "${WORKDIR}/repo"
