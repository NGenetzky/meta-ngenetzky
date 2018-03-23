SUMMARY = "Git for Windows, the Windows port of Git."
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PR = "r2"

inherit win-portable

SRC_URI = "\
    git://github.com/sheabunge/GitPortable.git;destsuffix=GitPortable \
	https://github.com/git-for-windows/git/releases/download/v2.17.0-rc0.windows.1/PortableGit-2.17.0.rc0.windows.1-64-bit.7z.exe \
"
SRCREV = "466af0343886626c07d791c33d0a7da8ab8ceacc"
SRC_URI[md5sum] = "0e99a541e03b9f8aee4f5232b9f1b70c"
SRC_URI[sha256sum] = "9d1a7bc97abefb004358416e34738109875d2bc76611785efc35155d9d60b116"

DEPENDS = " p7zip-native "

FILES_${PN} = "PortableApps/GitPortable/"
do_install() {
    local portabledir="${D}/PortableApps/GitPortable/"
    local appdir="${portabledir}/App/Git/"

    install -d \
        "${appdir}"
    cp -R --no-target-directory \
        "${WORKDIR}/GitPortable/GitPortable/" \
        "${portabledir}"
    7z x \
        -o"${appdir}" \
        "${WORKDIR}/PortableGit-2.17.0.rc0.windows.1-64-bit.7z.exe"
}


