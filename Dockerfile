FROM rocker/r-ver:4.1.1
RUN apt-get update && apt-get -y install --reinstall ca-certificates && update-ca-certificates
RUN apt-get update && apt-get -y install openjdk-11-jdk curl rlwrap libssl-dev build-essential zlib1g-dev  libncurses5-dev libgdbm-dev \
    libnss3-dev  libreadline-dev libffi-dev libbz2-dev  automake-1.15 git lzma-dev liblzma-dev

# python
RUN curl -O https://www.python.org/ftp/python/3.9.5/Python-3.9.5.tar.xz
RUN tar xf Python-3.9.5.tar.xz
RUN cd Python-3.9.5 && ./configure --enable-shared --with-ensurepip=install && make && make install && ldconfig
RUN curl -O https://download.clojure.org/install/linux-install-1.10.3.986.sh && chmod +x linux-install-1.10.3.986.sh && ./linux-install-1.10.3.986.sh
RUN clj -P
RUN pip3 install -U numpy wheel scikit-learn cython

#apl
RUN git clone https://git.savannah.gnu.org/git/apl.git
RUN cd apl/trunk && ./configure && make develop_lib && make install

RUN export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64 && pip3 install cljbridge

RUN apt-get install -y expect


RUN pip3 install torch simpletransformers

CMD [ "unbuffer", "python3", "-u","-c", "from clojurebridge import cljbridge\ncljbridge.init_jvm(start_repl=True,port=12345,bind='0.0.0.0',cider_nrepl_version='0.27.2')"]
