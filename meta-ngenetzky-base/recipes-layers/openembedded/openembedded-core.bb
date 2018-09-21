# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Layer containing the core metadata for current versions of OpenEmbedded"
PV = "2.4"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "7d518d342eb67d25aa071fb08d03f06d6da576c6"
SRC_URI = "git://github.com/openembedded/openembedded-core.git;branch=rocko"

S = "${WORKDIR}/git"
