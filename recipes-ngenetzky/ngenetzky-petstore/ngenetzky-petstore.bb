SUMMARY = "Playing around with swagger and docker (http://swagger.io) "
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PV = "1.0.0"
PR = "r2"

# Not machine or architecture dependent.
inherit allarch

# I dislike the git fetcher's default destsuffix. I prefer to keep the repo name.
SRCNAME = "ngenetzky-petstore"
S = "${WORKDIR}/${SRCNAME}"

SRC_URI = " \
	git://github.com/NGenetzky/${SRCNAME}.git;destsuffix=${SRCNAME};rev=008432e0788ecdccb20b3546535524b17b2e877a \
"

################################################################################
# ${PN}-dev
#
FILES_${PN}-dev = " \
	ngenetzky/${SRCNAME} \
"
dev_install() {
	install -d \ 
		"${D}/ngenetzky"
	cp -R -T \
		"${S}" \
		"${D}/ngenetzky/${SRCNAME}"
}
#
################################################################################

################################################################################
# Bitbake Tasks
#
# do_fetch
# do_unpack
do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	install -d \ 
		"${D}/ngenetzky"
	dev_install
}

# do_populate_sysroot
# do_package
#
################################################################################
