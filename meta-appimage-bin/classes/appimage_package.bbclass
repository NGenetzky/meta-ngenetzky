inherit bb_fetcher
addtask do_unpack before do_build

D ??= "${TOPDIR}/sysroot/appimage"
bindir ??= "bin"

addtask do_install before do_build after do_unpack
