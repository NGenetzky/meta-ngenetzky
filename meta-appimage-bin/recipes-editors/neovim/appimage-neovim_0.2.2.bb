SUMMARY = "Vim-fork focused on extensibility and usability"
HOMEPAGE = "https://neovim.io/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r1"

inherit appimage_package

# Custom Variable:
SRCFILENAME = "nvim-${PV}-x86_64.AppImage"

SRC_URI = " \
    https://github.com/neovim/neovim/releases/download/v0.2.2/nvim.appimage;downloadfilename=${SRCFILENAME} \
"
SRC_URI[md5sum] = "54d9ab248544ed535c46ee8677235567"
SRC_URI[sha256sum] = "809f6c24d78ebbbac5edb643deeb01b4dc06a54a712e4ebec381a98ab493ac73"

do_install() {
    local dest="${D}/${bindir}"
    local fname="${SRCFILENAME}"

    install -d "${dest}"
    install --target-directory "${dest}" \
        "${WORKDIR}/${fname}"
    ln -fsT \
        "${fname}" \
        "${dest}/nvim"
}
