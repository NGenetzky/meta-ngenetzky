inherit generator-swagger
SUMMARY = "Playing around with swagger and docker (http://swagger.io) "
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

INC_PR = "r1"

SRCNAME = "ngenetzky-api"
SRC_URI = "git://github.com/NGenetzky/${SRCNAME}.git;destsuffix=${SRCNAME}"
SWAGGER_URL = "https://raw.githubusercontent.com/NGenetzky/ngenetzky-api/${SRCREV}/api/swagger.yaml"

S = "${WORKDIR}/python-flask-server"

RDEPENDS_${PN} = "\
	${PYTHON_PN}-dateutil \
	${PYTHON_PN}-connexion \
"
