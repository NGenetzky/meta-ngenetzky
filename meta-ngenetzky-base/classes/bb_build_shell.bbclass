def export_func_shell(func, d, runfile, cwd=None):
    """Execute a shell function from the metadata

    Note on directory behavior.  The 'dirs' varflag should contain a list
    of the directories you need created prior to execution.  The last
    item in the list is where we will chdir/cd to.

    References:
        bitbake.bb.build.exec_func_shell()
    """
    with open(runfile, 'w') as script:
        bb.data.emit_func(func, script, d)

        if bb.msg.loggerVerboseLogs:
            script.write("set -x\n")
        if cwd:
            script.write("cd '%s'\n" % cwd)
        script.write("%s\n" % func)

do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts
python do_build_shell_scripts(){
}
