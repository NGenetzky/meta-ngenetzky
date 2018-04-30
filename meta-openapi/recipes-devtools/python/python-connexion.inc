SUMMARY = "API first applications with OpenAPI/Swagger and Flask"
HOMEPAGE = "https://github.com/zalando/connexion"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=1a59efe5ec370f84218405ce2e537845"

inherit pypi

INC_PR = "r1"

RDEPENDS_${PN} = "\
	${PYTHON_PN}-clickclick \
	${PYTHON_PN}-flask \
	${PYTHON_PN}-inflection \
	${PYTHON_PN}-jsonschema \
	${PYTHON_PN}-pathlib \
	${PYTHON_PN}-pyyaml \
	${PYTHON_PN}-requests \
	${PYTHON_PN}-six \
	${PYTHON_PN}-swagger-spec-validator \
	${PYTHON_PN}-typing \
"

PACKAGES =+ "${PN}-swagger-ui-dev"
RDEPENDS_${PN}-swagger-ui-dev = "${PN} bash"
FILES_${PN}-swagger-ui-dev = "\
	${PYTHON_SITEPACKAGES_DIR}/connexion/vendor/swagger-ui/update.sh \
"

