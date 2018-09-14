# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Collection of layers for the OE-core universe"
PV = "2.4"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "eae996301d9c097bcbeb8046f08041dc82bb62f8"
SRC_URI = "git://github.com/openembedded/meta-openembedded.git;branch=rocko"

S = "${WORKDIR}/git"
