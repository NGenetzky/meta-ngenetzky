die() {
	bbfatal "$*"
}

bbnote() {
	echo "NOTE:" "$*"
}

bbwarn() {
	echo "WARNING:" "$*"
}

bbfatal() {
	echo "FATAL:" "$*"
	exit 1
}

addtask listtasks
do_listtasks[nostamp] = "1"
python do_listtasks() {
	import sys
	for e in bb.data.keys(d):
		if d.getVarFlag(e, 'task', False):
			bb.plain("%s" % e)
}

addtask build
do_build[dirs] = "${TOPDIR}"
do_build[nostamp] = "1"
python base_do_build () {
	bb.note("The included, default BB base.bbclass does not define a useful default task.")
	bb.note("Try running the 'listtasks' task against a .bb to see what tasks are defined.")
}

EXPORT_FUNCTIONS do_build
