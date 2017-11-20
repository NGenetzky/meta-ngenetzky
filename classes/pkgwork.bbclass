
PKGWORK_WORKDIR = "${WORKDIR}/pkgwork"
PKGWORK_BASE = "${TMPDIR}/pkgwork"

PKGWORK_HISTORY = "${PKGWORK_BASE}/${PN}/${DATETIME}-${PV}-${PR}"
# Must use 'vardepsexclude' to remain sane for sstate cache.
PKGWORK_HISTORY[vardepsexclude] += "DATETIME"

PKGWORK_DIR = "${PKGWORK_BASE}/${PN}/${PV}-${PR}"

pkgwork_links() {
	# TODO: Use local var for dest.
	ln -fs \
		-T "${WORKDIR}" \
		${PKGWORK_WORKDIR}/link/WORKDIR
	ln -fs \
		-T "${D}" \
		${PKGWORK_WORKDIR}/link/D
	ln -fs \
		-T "${S}" \
		${PKGWORK_WORKDIR}/link/S
	ln -fs \
		-T "${B}" \
		${PKGWORK_WORKDIR}/link/B
	ln -fs \
		-T "${T}" \
		${PKGWORK_WORKDIR}/link/T
}

pkgwork_copy_log() {
	local task="${1?}"
	local src="${T}/log.${task}"
	install -m 664 \
		"${T}/log.${task}" \
		"${PKGWORK_WORKDIR}/log/log.${task}" \
		|| return 0
}

pkgwork_copy_run() {
	local task="${1?}"
	local src="${T}/run.${task}"
	install -m 664 \
		"${T}/run.${task}" \
		"${PKGWORK_WORKDIR}/run/run.${task}" \
		|| return 0
}

# addtask do_package_work
create_pkgwork() {
	install -d \
		${PKGWORK_WORKDIR}/log \
		${PKGWORK_WORKDIR}/link \
		${PKGWORK_WORKDIR}/run

	pkgwork_links

	pkgwork_copy_log 'do_fetch'
	pkgwork_copy_log 'do_configure'
	pkgwork_copy_log 'do_compile'
	pkgwork_copy_log 'do_install'
	pkgwork_copy_log 'do_populate_sysroot'
	pkgwork_copy_log 'do_package'

	pkgwork_copy_run 'do_fetch'
	pkgwork_copy_run 'do_configure'
	pkgwork_copy_run 'do_compile'
	pkgwork_copy_run 'do_install'
	pkgwork_copy_run 'do_populate_sysroot'
	pkgwork_copy_run 'do_package'
}

addtask do_pkgwork
do_pkgwork[nostamp] = "1"
do_pkgwork() {
	create_pkgwork
	install -d \
		${PKGWORK_DIR}
	cp -R -T \
		${PKGWORK_WORKDIR} \
		${PKGWORK_DIR}
	ln -s -f -T \
		${PKGWORK_DIR} \
		${PKGWORK_HISTORY}
}

