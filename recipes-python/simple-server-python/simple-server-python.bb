SUMMARY = "A Simple Server with Python Flask"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PV = "1.0.0"
PR = "r0"

# Use setuptools to do_compile and do_install.
inherit setuptools

SRCNAME = "simple_server_python"

SRC_URI = " \
	file://${SRCNAME}/* \
	file://setup.py \
"

################################################################################
# Bitbake Tasks
#
# do_fetch
# do_unpack
addtask do_unpack_package after do_unpack before do_configure
do_unpack_package() {
	install -d \
		"${S}/${SRCNAME}"
	# Project files
	install -m 644 \
		--target-directory "${S}" \
		${WORKDIR}/setup.py
	# Package files
	install -m 644 \
		--target-directory "${S}/${SRCNAME}" \
		${WORKDIR}/${SRCNAME}/*.*
}
# do_patch
# do_configure
# do_compile - setuptools
# do_install - setuptools
# do_populate_sysroot
# do_package
#
################################################################################
