inherit image

ROOTFS_BOOTSTRAP_INSTALL = ""
LINGUAS_INSTALL = ""
IMAGE_INSTALL = ""

IMAGE_PREPROCESS_COMMAND += "cleanup_rootfs"
cleanup_rootfs () {
    rm \
        ${IMAGE_ROOTFS}/etc/ld.so.cache \
        ${IMAGE_ROOTFS}/var/cache/ldconfig/aux-cache
    find "${IMAGE_ROOTFS}" -depth -type d -empty -exec rmdir {} \;
}

# We will assume we don't want to boot this image.
do_bootimg[noexec] = "1"
do_bootimg[depends] = ""
IMAGE_FSTYPES = "tar.xz"

