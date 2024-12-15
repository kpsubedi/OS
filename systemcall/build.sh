#!/bin/bash

filename=$1
nasm -felf64 $filename.nasm -o $filename.o
ld $filename.o -o $filename
