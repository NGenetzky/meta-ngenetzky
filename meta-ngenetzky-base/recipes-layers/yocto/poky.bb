SUMMARY = "Poky Build Tool and Metadata"
PV = "2.4"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "05711ba18587aaaf4a9c465a1dd4537f27ceda93"
SRC_URI = "git://git.yoctoproject.org/poky.git;branch=rocko"

S = "${WORKDIR}/git"
