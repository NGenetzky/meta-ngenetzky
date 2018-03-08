SUMMARY = "Bash-it is a collection of community Bash commands and scripts for Bash 3.2+."
DESCRIPTION = " Includes autocompletion, themes, aliases, custom functions, a \
few stolen pieces from Steve Losh, and more. \
 \
Bash-it provides a solid framework for using, developing and maintaining shell \
scripts and custom commands for your daily work. If you're using the Bourne \
Again Shell (Bash) on a regular basis and have been looking for an easy way on \
how to keep all of these nice little scripts and aliases under control, then \
Bash-it is for you! Stop polluting your ~/bin directory and your .bashrc file, \
fork/clone Bash-it and start hacking away. \
"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PV = "1.0+git${SRCPV}"
PR = "r1"

SRC_URI = "\
	git://github.com/Bash-it/bash-it.git \
"
SRCREV = "c46b72d8d6feff54b81b1f198c124f8cdf24fd58"
S = "${WORKDIR}/git"

cp_from_bashit() {
	local path="${1?}"
	cp -R --no-target-directory \
		"${S}/${path}" \
		"${D}/${ROOT_HOME}/.bash-it/${path}"
}

FILES_${PN} = "${ROOT_HOME}"
do_install() {
	install -d \
		"${D}/${ROOT_HOME}/.bash-it/"
	# TODO: Technically shouldn't use cp.
	cp_from_bashit 'aliases'
	cp_from_bashit 'completion'
	cp_from_bashit 'custom'
	cp_from_bashit 'lib'
	cp_from_bashit 'plugins'
	cp_from_bashit 'themes'
	install -m 644 --target-directory \
		"${D}/${ROOT_HOME}/.bash-it/" \
		"${S}/bash_it.sh" \
		"${S}/install.sh" \
		"${S}/uninstall.sh"
}

