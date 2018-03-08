SUMMARY = "Small yet usable docker devbox."
PV = "1.0"
PR = "r2"

IMAGE_FEATURES += "splash ssh-server-openssh"

IMAGE_INSTALL = "\
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    "

IMAGE_INSTALL_append = "\
    bash \
    curl \
    git \
    make \
    man \
    python \
    tmux \
    vim \
"

IMAGE_INSTALL_append = "\
    bash-it \
    tpm \
    dotfiles \
"

inherit core-image

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE_append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"

