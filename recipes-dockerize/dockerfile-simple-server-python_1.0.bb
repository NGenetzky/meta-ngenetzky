SUMMARY = "A Simple Server with Python Flask"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PV = "1.0"
PR = "r1"

SRC_URI = "\
	file://Dockerfile \
	file://requirements.txt \
"


fetch_rdepends_debs[depends] = "\
	simple-server-python:do_package_write_deb \
"
addtask fetch_rdepends_debs after do_fetch before do_compile
do_fetch_rdepends_debs() {
	install -d "${S}/install-debs/"
	install --target-directory "${S}/install-debs/" \
		${DEPLOY_DIR_DEB}/${PACKAGE_ARCH}/simple-server-python_*.deb
}


do_install() {
	install -d \
		"${D}/${datadir}/${PN}" \
		"${D}/${datadir}/${PN}/install-debs"

	install --target-directory "${D}/${datadir}/${PN}" \
		"${WORKDIR}/Dockerfile" \
		"${WORKDIR}/requirements.txt"

	install --target-directory "${D}/${datadir}/${PN}/install-debs" \
		${S}/install-debs/*
}
FILES_${PN} = "${datadir}/${PN}"


