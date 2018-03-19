SUMMARY = "JSON API to DB: Fetch JSON from APIs and send to a TinyDB database."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

INC_PR = "r0"

SRCNAME = "jatdb"
SRC_URI = "git://github.com/NGenetzky/${SRCNAME}.git;subpath=python-flask-server;branch=swagger_server;destsuffix=${SRCNAME}"

S = "${WORKDIR}/python-flask-server"
SWAGGER_URL = "https://raw.githubusercontent.com/NGenetzky/${SRCNAME}/${SRCREV}/python-flask-server/swagger_server/swagger/swagger.yaml"
inherit generator-swagger

do_patch() {
    install -d "${S}/jatdb_server/clients"
    install -m 644 --target-directory "${S}/jatdb_server/clients" \
        ${WORKDIR}/jatdb/swagger_server/clients/*
    install -m 644 --no-target-directory \
        "${WORKDIR}/jatdb/requirements.txt" \
        "${S}/requirements.txt"
}

RDEPENDS_${PN} = "\
	${PYTHON_PN}-connexion \
	${PYTHON_PN}-dateutil \
	${PYTHON_PN}-tinydb \
"

