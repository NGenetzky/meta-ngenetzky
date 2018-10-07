SUMMARY = "PulseView is a Qt based logic analyzer, oscilloscope and MSO GUI for sigrok."
HOMEPAGE = "https://sigrok.org/wiki/Downloads#Linux_AppImage_binaries"

PV = "2018.10.06"
PR = "r0"

PLATFORM="native-x86_64-appimage"
FNAME="PulseView-NIGHTLY-x86_64.AppImage"

inherit appimage_package

SRC_URI = " \
    https://sigrok.org/jenkins/job/sigrok-native-appimage/lastSuccessfulBuild/platform=${PLATFORM}/artifact/cross-compile/appimage/out/${FNAME} \
"

do_install() {
    local dest="${D}/${bindir}"
    local fname="${FNAME}"

    install -d "${dest}"
    install --target-directory "${dest}" \
        "${WORKDIR}/${fname}"
    ln -fsT \
        "${fname}" \
        "${dest}/pulseview"
}
