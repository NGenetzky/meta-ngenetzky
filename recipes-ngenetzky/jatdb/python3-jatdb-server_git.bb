SUMMARY = "Playing around with swagger and docker (http://swagger.io) "
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PV = "0.0.1-git${SRCPV}"
PR = "r0"

SRCNAME = "python-flask-server"
SRC_URI = "\
	git://github.com/NGenetzky/jatdb.git;branch=swagger_server;subpath=python-flask-server \
"
SRCREV = "2445960faf25df6c078573e303bdc4f56a5803a4"
S = "${WORKDIR}/${SRCNAME}"

inherit setuptools3

RDEPENDS_${PN} = "\
	${PYTHON_PN}-dateutil \
	${PYTHON_PN}-connexion \
"
