#!/bin/bash

title1="IntelliJ Idea"
title2="React"
title3="VSCode"

gnome-terminal --tab --title="$title1" -e 'bash -c "cd ./API; idea ."' \
               --tab --title="$title2" -e 'bash -c "cd ./Frontend; npm run start"' \
               --tab --title="$title3" -e 'bash -c "cd ./Frontend; code ."'