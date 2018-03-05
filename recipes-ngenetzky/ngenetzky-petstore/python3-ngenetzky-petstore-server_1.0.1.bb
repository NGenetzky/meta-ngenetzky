SUMMARY = "Playing around with swagger and docker (http://swagger.io) "
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PV = "1.0.1"
PR = "r0"

SRCNAME = "server_python_flask"
SRC_URI = "\
	git://github.com/NGenetzky/ngenetzky-petstore.git;branch=server/python_flask;subpath=swagger-codegen/${SRCNAME} \
"
SRCREV = "8933a2a4bbdfd4ea287114f1597c9700bcab9624"
S = "${WORKDIR}/${SRCNAME}"

inherit setuptools3

RDEPENDS_${PN} = "\
	${PYTHON_PN}-dateutil \
	${PYTHON_PN}-connexion \
"
