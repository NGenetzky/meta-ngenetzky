SUMMARY = "Click utility functions"
DESCRIPTION = " Utility functions (Python 3 only) for the wonderful \
[Click library](http://click.pocoo.org/5/). Click is a Python package for \
creating beautiful command line interfaces in a composable way with as little \
code as necessary. \
"
HOMEPAGE = "https://github.com/hjacobs/python-clickclick"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=e25d313961b3f5960f7f2ca88113dfaa"

inherit pypi

RDEPENDS_${PN} = "\
	${PYTHON_PN}-click \
	${PYTHON_PN}-pyyaml \
"
