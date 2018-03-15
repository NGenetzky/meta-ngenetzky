SUMMARY = "resin.io + python + raspberry pi camera module"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
INC_PR = "r0"

SRC_URI = "\
	git://github.com/resin-io-projects/resin-rpi-python-picamera.git \
"
S = "${WORKDIR}/git"
B = "${WORKDIR}/${PN}"

# Custom Variables
PYPACKAGE='resin_picamera'

do_patch(){
    install -d \
        "${B}/${PYPACKAGE}"
    install --no-target-directory \
        "${S}/demo.py" \
        "${B}/${PYPACKAGE}/__init__.py"
}

addtask do_generate_setup after do_patch before do_compile
python do_generate_setup() {
    setup_file = os.path.join(d.getVar('B'),'setup.py')

    package = d.getVar('PYPACKAGE')
    contents = \
'''from setuptools import setup
setup(
    name='{0}',
    version='{1}',
    description='{2}',
    license='{3}',
    packages=['{4}'],
)
'''.format(
        package,
        d.getVar('PV'),
        d.getVar('SUMMARY'),
        d.getVar('LICENSE'),
        package,
    )

    with open(setup_file, 'w') as f:
        f.write(contents)
}

RDEPENDS_${PN} = "\
	${PYTHON_PN}-picamera \
"

