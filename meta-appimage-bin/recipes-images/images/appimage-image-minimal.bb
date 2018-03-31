DESCRIPTION = "Image designed for App Images"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PV = "0.0.1"
PR = "r1"

inherit image

IMAGE_INSTALL = "\
    appimage-etcher \
    appimage-gvim \
    appimage-neovim \
    appimage-vlc \
"

