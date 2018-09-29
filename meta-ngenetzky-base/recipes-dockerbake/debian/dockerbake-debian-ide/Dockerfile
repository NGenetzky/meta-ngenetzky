FROM debian:jessie
LABEL \
    maintainer="Nathan@Genetzky.us"

COPY [ "./build_gosu.sh", "./build_c9.sh", "./entrypoint.sh", "./init.sh", "/tmp/" ]

RUN /tmp/build_gosu.sh
RUN /tmp/build_c9.sh

ENTRYPOINT ["bash", "/tmp/entrypoint.sh"]
CMD "/bin/bash /tmp/init.sh"
# CMD ["/bin/bash", "/tmp/init.sh"]
