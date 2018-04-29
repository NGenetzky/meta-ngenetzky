SUMMARY = "Open source cross-platform multimedia player and framework"
HOMEPAGE = "https://www.videolan.org/vlc/index.html"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PR = "r1"

inherit appimage_package

# Custom Variable:
SRCFILENAME = "VLC-${PV}-x86_64.AppImage"

SRC_URI = " \
    https://bintray.com/probono/AppImages/download_file?file_path=${SRCFILENAME};downloadfilename=${SRCFILENAME} \
"
SRC_URI[md5sum] = "c2c2cafc405c87b5e3b3e66f23d47548"
SRC_URI[sha256sum] = "03ed95ad97d78d60ea010f4686d435ced87a47a6828f80dcb043207e513d807b"

do_install() {
    local dest="${D}/${bindir}"
    local fname="${SRCFILENAME}"

    install -d "${dest}"
    install --target-directory "${dest}" \
        "${WORKDIR}/${fname}"
    ln -fsT \
        "${fname}" \
        "${dest}/vlc"
}
