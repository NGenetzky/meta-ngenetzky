FROM ubuntu:16.04
LABEL \
    maintainer="Nathan@Genetzky.us"

COPY [ "./build_0.sh", "/tmp/" ]

RUN /tmp/build_0.sh

ENTRYPOINT ["/usr/games/cowsay"]
CMD ["This was built in a bitbake context!"]
