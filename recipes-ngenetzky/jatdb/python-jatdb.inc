SUMMARY = "JSON API to DB: Fetch JSON from APIs and send to a TinyDB database."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

INC_PR = "r0"

SRCNAME = "jatdb"
SRC_URI = "git://github.com/NGenetzky/jatdb.git;destsuffix=${SRCNAME}"
S = "${WORKDIR}/${SRCNAME}"

RDEPENDS_${PN} = "\
	${PYTHON_PN}-tinydb \
"

