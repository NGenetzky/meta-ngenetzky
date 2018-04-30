SUMMARY = "Validation of Swagger specifications"
HOMEPAGE = "http://github.com/Yelp/swagger_spec_validator"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=388f6046a59d07141701be640c681d35"

inherit pypi

RDEPENDS_${PN} = "\
	${PYTHON_PN}-jsonschema \
	${PYTHON_PN}-pyyaml \
	${PYTHON_PN}-six \
"
