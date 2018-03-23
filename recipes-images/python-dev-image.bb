DESCRIPTION = "Image usable for Python Development"
HOMEPAGE = "nathan.genetzky.us"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PV = "0.0.1"
PR = "r1"

inherit core-image

IMAGE_FEATURES = "\
	debug-tweaks \
	package-management \
"

IMAGE_INSTALL = "\
	${CORE_IMAGE_EXTRA_INSTALL} \
	packagegroup-core-boot \
	packagegroup-core-full-cmdline \
"

IMAGE_INSTALL += "\
	oas2-petstore-html \
	python3-oas2-petstore \
	python3-oas2-petstore-server \
"

