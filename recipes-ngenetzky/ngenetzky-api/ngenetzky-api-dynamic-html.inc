inherit generator-swagger

DESCRIPTION = "Playing out an idea I have using swagger."
HOMEPAGE = "https://github.com/NGenetzky/ngenetzky-api"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

INC_PR = "r1"

SRCNAME = "ngenetzky-api"
SRC_URI = "git://github.com/NGenetzky/${SRCNAME}.git;destsuffix=${SRCNAME}"
SWAGGER_URL = "https://raw.githubusercontent.com/NGenetzky/ngenetzky-api/${SRCREV}/api/swagger.yaml"

SWAGGER_ENDPOINT = "clients/dynamic-html"
S = "${WORKDIR}/dynamic-html-client"

