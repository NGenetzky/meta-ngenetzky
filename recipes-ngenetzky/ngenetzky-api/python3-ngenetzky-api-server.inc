SUMMARY = "Playing around with swagger and docker (http://swagger.io) "
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PV = "0.0.1-git${SRCPV}"
PR = "r0"

SRCNAME = "python-flask-server"
SRC_URI = "git://github.com/NGenetzky/ngenetzky-api.git;branch=gen/0.0.1;subpath=gen/servers/${SRCNAME}"
S = "${WORKDIR}/${SRCNAME}"
SRCREV = "cfa7308cfbe8d6381dc94a33a2d41bbd4f15cbea"

RDEPENDS_${PN} = "\
	${PYTHON_PN}-dateutil \
	${PYTHON_PN}-connexion \
"
