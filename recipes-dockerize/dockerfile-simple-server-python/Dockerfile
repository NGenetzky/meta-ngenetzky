FROM resin/nuc-python
# FROM resin/%%RESIN_MACHINE_NAME%%-python

# Copy requirements.txt first for better cache on later pushes
# pip install python deps from requirements.txt on the resin.io build server
COPY ./requirements.txt /requirements.txt
RUN pip install -r /requirements.txt


COPY ./install-debs/* \
    /tmp/
RUN dpkg --install /tmp/*.deb

# switch on systemd init system in container
# ENV INITSYSTEM on

CMD ["python","/usr/bin/simple_server_python.py"]

