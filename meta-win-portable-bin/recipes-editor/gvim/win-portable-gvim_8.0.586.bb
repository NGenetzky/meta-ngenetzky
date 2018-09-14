SUMMARY = "Powerful, feature-rich and highly configurable text editor primarily for programmers."
LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

PR = "r1"

inherit win-portable

SRC_URI = "\
	https://downloads.sourceforge.net/portableapps/gVimPortable_8.0.586.paf.exe \
"
SRC_URI[md5sum] = "2c3c21383cefc5dc4426ec2dcaa02ba4"
SRC_URI[sha256sum] = "ba732d9bb6d2abd553d1255586df6eb43b7307376f659db0c10c02a23e6a4aff"

# DEPENDS = " p7zip-native "

FILES_${PN} = "PortableApps/gVimPortable/"
do_install() {
    local portabledir="${D}/PortableApps/gVimPortable/"
    local pafdir="${WORKDIR}/paf/"

    install -d \
        "${portabledir}" \
        "${pafdir}"

    7z x -y \
        -o"${pafdir}" \
        ${WORKDIR}/gVimPortable_8.0.586.paf.exe

    install -m 664 --target-directory "${portabledir}" \
        ${pafdir}/gVimPortable.exe \
        ${pafdir}/help.html

    cp -R --no-target-directory \
        "${pafdir}/App" \
        "${portabledir}/App"
pafdir
    cp -R --no-target-directory \
        "${pafdir}/Other" \
        "${portabledir}/Other"
}


