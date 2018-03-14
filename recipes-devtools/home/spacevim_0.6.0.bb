SUMMARY = "A community-driven modular vim distribution inspired by spacemacs"
DESCRIPTION = " SpaceVim is a distribution of the vim editor that's inspired by \
spacemacs. It manages collections of plugins in layers, which help collect \
related packages together to provide features. For example, the python layer \
collects deoplete.nvim, neomake and jedi-vim together to provides \
autocompletion, syntax checking, and documentation lookup. This approach helps \
keep configuration organized and reduces overhead for the user by keeping them \
from having to think about what packages to install. \
"
HOMEPAGE = "https://spacevim.org"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5daa669807b4cc04d5bd1046a3bb5509"

PR = "r1"

inherit allarch

SRC_URI = "\
	git://github.com/SpaceVim/SpaceVim.git;destsuffix=${PN}-${PV} \
"
SRCREV = "50556eb7458990fcc7946c897888472a8a73a13c"
B="${WORKDIR}/home"

do_compile() {
	ln -Tfs ".SpaceVim" \
		"${B}/.vim"
	git clone \
		"https://github.com/Shougo/dein.vim.git" \
		"${B}/.cache/vimfiles/repos/github.com/Shougo/dein.vim"
}

cp_from_spacevim() {
	local path="${1?}"
	cp -R --no-target-directory \
		"${S}/${path}" \
		"${D}/${ROOT_HOME}/.SpaceVim/${path}"
}

FILES_${PN} = "${ROOT_HOME}"
do_install() {
	install -d \
		"${D}/${ROOT_HOME}/.SpaceVim"
	cp -R --no-target-directory \
		"${B}/" \
		"${D}/${ROOT_HOME}/"
	cp_from_spacevim 'autoload'
	cp_from_spacevim 'bin'
	cp_from_spacevim 'config'
	cp_from_spacevim 'doc'
	cp_from_spacevim 'ftplugin'
	cp_from_spacevim 'mode'
	cp_from_spacevim 'syntax'
	install -m 644 --target-directory "${D}/${ROOT_HOME}/.SpaceVim/" \
		"${S}/filetype.vim" \
		"${S}/ginit.vim" \
		"${S}/init.vim" \
		"${S}/vimrc"
}

# These are required for dein.vim.
RDEPENDS_${PN} += "git git-perltools"

