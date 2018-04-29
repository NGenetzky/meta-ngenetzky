
DEPENDS += "\
    glib-2.0 \
    zlib \
"

do_compile[noexec] = "1"
do_configure[noexec] = "1"

# INSANE_SKIP_${PN} += "build-deps"
INSANE_SKIP_${PN} += "file-rdeps"
INSANE_SKIP_${PN} += "already-stripped"

