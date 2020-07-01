# Copyright (C) 2020 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)
require kas-poky.inc

PV = "3.1.1"
PR = "${INC_PR}.0"

SRC_URI = "\
    http://downloads.yoctoproject.org/releases/yocto/yocto-3.1.1/toolchain/x86_64/poky-glibc-x86_64-core-image-minimal-core2-64-qemux86-64-toolchain-ext-3.1.1.sh \
"

SRC_URI[md5sum] = ""
SRC_URI[sha256sum] = "c8dcfd0a590d2cba846a72fc2aefaabe79048382b96610bdfe992cb0d95cafef"
