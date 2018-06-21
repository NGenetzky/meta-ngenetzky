SUMMARY = "Personal Bitbake Layer"
PV = "2018.07.20"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "03b54900f35917e9b70a3f48c2a0ae2069f3d47e"
SRC_URI = "git://github.com/NGenetzky/${PN}.git"
S = "${WORKDIR}/git"
