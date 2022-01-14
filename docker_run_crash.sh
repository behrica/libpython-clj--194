#!/usr/bin/env bash
set -euo pipefail
docker build . -t libpython-clj-crash
docker run -it --rm -v "$(pwd):/workdir" -p  12345:12345 -w /workdir libpython-clj-crash
