SUMMARY = "A note taking and to-do application with synchronization capabilities."
HOMEPAGE = "https://joplin.cozic.net/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PV = "1.0.106"
PR = "r1"

inherit appimage_package

# Custom Variable:
SRCFILENAME = "Joplin-${PV}-x86_64.AppImage"

SRC_URI = "https://github.com/laurent22/joplin/releases/download/v${PV}/Joplin-${PV}-x86_64.AppImage;downloadfilename=${SRCFILENAME}"
SRC_URI[md5sum] = "e0a07d5ceade45eab362fddfffce70c7"
SRC_URI[sha256sum] = "fe7cdc3a7f86d6e949acb73fdbded59b0c0bbf46f87fd3fce2e6686a9a1e8fcb"

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
