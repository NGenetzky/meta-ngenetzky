# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Playing out an idea I have using swagger."
HOMEPAGE = "https://github.com/NGenetzky/ngenetzky-api"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
# SECTION = ""
# DEPENDS = ""

PV = "0.0.1"
PR = "r1"
SRCREV = "20761760531afb3b9fe36c0741dd6e151623f899"

# I dislike the git fetcher's default destsuffix. I prefer to keep the repo name.
SRCNAME = "${PN}"
S = "${WORKDIR}/${SRCNAME}"
SRC_URI = "\
	git://github.com/NGenetzky/${SRCNAME}.git;destsuffix=${SRCNAME};nobranch=1 \
"

# Not machine or architecture dependent.
inherit allarch

