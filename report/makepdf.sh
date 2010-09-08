#!/bin/sh
dvips -Ppdf thesis.dvi
NAME=thesis
ps2pdf13 -dAutoFilterColorImages=false -dColorImageFilter=/FlateEncode $NAME.ps $NAME.pdf
