
# This can be used to generate a tar for every package.
inherit package_tar

addtask do_devpackage_install after do_install before do_deploy
do_devpackage_install() {
    install -d \
        "${D}/devpackage/TOPDIR/tmp/work/${PN}/${PV}-${PR}/${PN}-${PV}" \
        "${D}/devpackage/TOPDIR/tmp/work/${PN}/${PV}-${PR}/temp" \
        "${D}/devpackage/TOPDIR/tmp/work/${PN}/${PV}-${PR}/image" \
        "${D}/devpackage/workspace/"
    ln -s \
        ../TOPDIR/tmp/work/${PN}/${PV}-${PR}/ \
        "${D}/devpackage/workspace/WORKDIR"
    ln -s \
        ./WORKDIR/temp/ \
        "${D}/devpackage/workspace/T"
    ln -s \
        ./WORKDIR/${PN}-${PV}/ \
        "${D}/devpackage/workspace/S"
    ln -s \
        ./WORKDIR/image/ \
        "${D}/devpackage/workspace/D"
    ln -s \
        ./S/ \
        "${D}/devpackage/workspace/B"
}

PACKAGES += "${PN}-devpackage"
FILES_${PN}-devpackage = " \
	/devpackage/* \
"

