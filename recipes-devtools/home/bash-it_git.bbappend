
PR_append = ".0"

do_compile_append() {
	bashit_enable 'aliases' 'docker'
	bashit_enable 'aliases' 'docker-compose'
	bashit_enable 'aliases' 'general'
	bashit_enable 'aliases' 'git'

	# bashit_enable 'plugin' 'base'
	# bashit_enable 'plugin' 'git'

	# bashit_enable 'completion' 'alias'
	bashit_enable 'completion' 'bash-it'
	bashit_enable 'completion' 'system'
}
