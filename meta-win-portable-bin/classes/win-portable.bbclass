
# TODO: Do we need to consider "x86"?
PACKAGE_ARCH = "x86_64"

# All RDEPENDS are prepackaged.
INSANE_SKIP_${PN} += "file-rdeps"

