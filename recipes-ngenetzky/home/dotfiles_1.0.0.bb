SUMMARY = "My goal is to utilize existing frameworks to create a modular dotfile collection. "
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0a1d42d49e7406a6bdc8bb01e7bc9677"

PR = "r0"

SRC_URI = "\
	git://github.com/NGenetzky/dotfiles.git;destsuffix=${PN}-${PV} \
"
SRCREV = "3260b8cbb7ea36e4806801d2fd094bb6398e88d5"

USER = "dev"

do_compile() {
	install -d \
		"${B}/home/"
	git clone \
		https://github.com/NGenetzky/dotfiles.git \
		"${B}/home/.dotfiles"
}

FILES_${PN} = "/home/${USER}"
do_install() {
	install -d \
		"${D}/home/${USER}"
	cp -R --no-target-directory \
		"${B}/home/" \
		"${D}/home/${USER}/"
}

