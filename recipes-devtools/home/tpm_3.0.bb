SUMMARY = "Installs and loads TMUX plugins."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=fa6431b4e7d52f284e4162879cab16d1"

PV = "3.0+git${SRCPV}"
PR = "r1"

inherit allarch

SRC_URI = "\
	git://github.com/tmux-plugins/tpm.git \
"
SRCREV = "1ff32085b2b30956fbab58b1520d84d95f18d48d"
S = "${WORKDIR}/git"

cp_from_tpm() {
	local path="${1?}"
	cp -R --no-target-directory \
		"${S}/${path}" \
		"${D}/${ROOT_HOME}/.tmux/plugins/tpm/${path}"
}

FILES_${PN} = "${ROOT_HOME}"
do_install() {
	install -d \
		"${D}/${ROOT_HOME}/.tmux/plugins/tpm/"
	# TODO: Technically shouldn't use cp.
	cp_from_tpm 'bin'
	cp_from_tpm 'bindings'
	cp_from_tpm 'lib'
	cp_from_tpm 'scripts'
	install -m 644 --target-directory "${D}/${ROOT_HOME}/.tmux/plugins/tpm/" \
		"${S}/tpm"
}

