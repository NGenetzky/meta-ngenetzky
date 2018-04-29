SUMMARY = "Burn images to SD cards & USB drives, safely and easily."
HOMEPAGE = "https://etcher.io/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r1"

inherit appimage_package

SRC_URI = " \
    https://github.com/resin-io/etcher/releases/download/v${PV}/etcher-${PV}-linux-x86_64.zip \
"
SRC_URI[md5sum] = "3286b3b1b6b427feae10cf029942c9be"
SRC_URI[sha256sum] = "e11d29d79122825d732ac6d4eff86cf517ce6f84afe6aa4856705da16bfaeebf"

do_install() {
    local dest="${D}/${bindir}"
    local fname="etcher-${PV}-x86_64.AppImage"

    install -d "${dest}"
    install --target-directory "${dest}" \
        "${WORKDIR}/${fname}"
    ln -fsT \
        "${fname}" \
        "${dest}/etcher"
}
