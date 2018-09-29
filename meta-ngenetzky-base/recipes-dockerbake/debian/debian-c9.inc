build_c9_apps(){
    apt-get update && apt-get install -y \
            python \
            build-essential \
            libssl-dev \
            apache2-utils \
            libxml2-dev \
            git \
            curl \
            locales \
            tmux \
        && rm -rf /var/lib/apt/lists/*
}
build_c9_locale(){
    sed -i -e 's/# en_US.UTF-8 UTF-8/en_US.UTF-8 UTF-8/' /etc/locale.gen \
        && locale-gen
}
build_c9_nvm(){
    git clone https://github.com/creationix/nvm.git ~/.nvm \
        && bash -c "source ~/.nvm/nvm.sh && nvm install 0.10;"
}
build_c9_c9sdk(){
    git clone https://github.com/c9/core.git ~/c9sdk \
        && rm -rf ~/c9sdk/.git \
        && cd ~/c9sdk \
        && ./scripts/install-sdk.sh
}
build_c9_cleanup(){
    apt-get remove -y \
            build-essential \
        && apt-get autoremove -y
}
build_c9(){
    build_c9_apps
    build_c9_locale
    build_c9_nvm
    build_c9_c9sdk
    build_c9_cleanup
}
