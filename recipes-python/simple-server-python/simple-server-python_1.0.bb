SUMMARY = "A Simple Server with Python Flask"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PV = "1.0"
PR = "r1"
SRCNAME = "simple_server_python"

SRCREV = "319b8654758f4bc4498945c8634a8cbc03a79276"
SRC_URI = "\
	git://github.com/NGenetzky/${PN}.git;destsuffix=${PN}-${PV} \
"

do_install() {
	install -d "${D}/${bindir}/"
	install --no-target-directory \
		"${S}/src/main.py" \
		"${D}/${bindir}/simple_server_python.py"
}
FILES_${PN} += "${bindir}/"

