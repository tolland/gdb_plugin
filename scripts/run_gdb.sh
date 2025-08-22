#!/bin/bash

set -eu -o pipefail

gdb --command=file.gdb --args some-prog --option arg
