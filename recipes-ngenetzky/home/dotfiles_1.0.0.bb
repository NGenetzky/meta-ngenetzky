SUMMARY = "My goal is to utilize existing frameworks to create a modular dotfile collection. "
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0a1d42d49e7406a6bdc8bb01e7bc9677"

PR = "r2"

SRC_URI = "\
	git://github.com/NGenetzky/dotfiles.git;destsuffix=${PN}-${PV} \
"
SRCREV = "3260b8cbb7ea36e4806801d2fd094bb6398e88d5"

do_compile() {
	local homedir="${B}/home/"
	install -d \
		"${homedir}"
	git clone \
		https://github.com/NGenetzky/dotfiles.git \
		"${B}/home/.dotfiles"
	# bash
	ln -Tfs ".dotfiles/config/bash/bashrc" \
		"${homedir}/.bashrc"
	ln -Tfs ".dotfiles/config/bash/profile" \
		"${homedir}/.profile"
	ln -Tfs ".dotfiles/config/bash/bash_logout" \
		"${homedir}/.bash_logout"
	# bash-it
	install -d \
		"${homedir}/.bash_it"
	ln -sfT "../.dotfiles/bash/bash-it/custom/" \
		"${homedir}/.bash_it/custom"
	# tmux
	install -d \
		"${homedir}/.tmux/"
	ln -Tfs ".dotfiles/tmux/themes" \
		"${homedir}/.tmux/themes"
}

FILES_${PN} = "${ROOT_HOME}"
do_install() {
	install -d \
		"${D}/${ROOT_HOME}"
	cp -R --no-target-directory \
		"${B}/home/" \
		"${D}/${ROOT_HOME}/"
}

