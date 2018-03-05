
# Custom Variables for dockerfile-image.
def dockerfile_repo(d):
    bpn = d.getVar('BPN')
    if bpn.startswith('dockerfile-'):
        return bpn[11:]
    return bpn
DOCKERFILE_IMAGE ?= "core-image-minimal"
DOCKERFILE_REPO ?= "${@dockerfile_repo(d)}"
DOCKERFILE_TAG ?= "${PV}-${PR}"


DEPENDS = "\
	${DOCKERFILE_IMAGE} \
"
fetch_images[depends] = "\
	${DOCKERFILE_IMAGE}:do_image_tar \
"
addtask fetch_images after do_fetch before do_compile
do_fetch_images() {
	local imagesdir="${S}/${DOCKERFILE_REPO}/images/"
	install -d \
		"${imagesdir}"
	# This assumes: IMAGE_FSTYPES += "tar.xz"
	install -m 664 --target-directory "${imagesdir}" \
		"${DEPLOY_DIR_IMAGE}/${DOCKERFILE_IMAGE}-${MACHINE}.tar.xz"
	echo "docker import ${DOCKERFILE_IMAGE}-${MACHINE}.tar.xz ${DOCKERFILE_REPO}:${DOCKERFILE_TAG}" \
		> "${imagesdir}/${DOCKERFILE_REPO}_${DOCKERFILE_TAG}.docker-import.sh"
	echo "FROM ${DOCKERFILE_REPO}:${DOCKERFILE_TAG}" \
		> "${imagesdir}/${DOCKERFILE_REPO}_${DOCKERFILE_TAG}.Dockerfile"
}


FILES_${PN} = "\
    ${datadir}/${PN} \
"
do_install() {
	local contextdir="${D}/${datadir}/${PN}/${DOCKERFILE_REPO}/"
	install -d \
		"${D}/${datadir}/${PN}" \
		"${D}/${datadir}/${PN}/${DOCKERFILE_REPO}" \
		"${D}/${datadir}/${PN}/${DOCKERFILE_REPO}/images"

	install --target-directory "${contextdir}/images" \
		${S}/${DOCKERFILE_REPO}/images/*sh
	install -m 644 --target-directory "${contextdir}/images" \
		${S}/${DOCKERFILE_REPO}/images/*tar.xz \
		${S}/${DOCKERFILE_REPO}/images/*Dockerfile

	echo "images/" \
		> "${contextdir}/.dockerignore"

	install -m 644 --no-target-directory \
		${S}/${DOCKERFILE_REPO}/images/${DOCKERFILE_REPO}_${DOCKERFILE_TAG}.Dockerfile \
		"${contextdir}/Dockerfile"
}


inherit deploy
addtask deploy after do_install
do_deploy() {
	local contextdir="${D}/${datadir}/${PN}/${DOCKERFILE_REPO}/"
	tar --create --xz \
		--file "${DEPLOYDIR}/${DOCKERFILE_REPO}_${DOCKERFILE_TAG}.dockerfile.tar.xz" \
		--directory "${contextdir}" \
		./
	ln -sfT \
		"${DOCKERFILE_REPO}_${DOCKERFILE_TAG}.dockerfile.tar.xz" \
		"${DOCKERFILE_REPO}.dockerfile.tar.xz"
}

