SUMMARY = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=4ace54e302f46e27f42b046a53d25ff2"
SECTION = "web"

PV = "1.0-git${SRCPV}"

SRCREV = "d558f5382cc91f674d8236ed7ed3a86aff0830f7"
SRC_URI = "\
	gitsm://github.com/NGenetzky/hugo-academic-kickstart.git \
"
S = "${WORKDIR}/git"

do_compile() {
    hugo \
        --config "${S}/config.toml"
}

