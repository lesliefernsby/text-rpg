#!/bin/bash

# Load environment variables from .env
export $(grep -v '^#' .env | xargs)

# Run Maven
mvn clean install
