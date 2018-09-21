SUMMARY = "Personal Bitbake Layer"
PV = "2018.09.20"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "79ef3bc9d39a24d23410c39fbe6a6aa5b18eae2c"
SRC_URI = "git://github.com/NGenetzky/${PN}.git"
S = "${WORKDIR}/git"
