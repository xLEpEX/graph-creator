#!/bin/sh

set -e

export APP_NAME="graph-api"
export NAMESPACE="qvantum"

export IMAGE="docker.io/xlepex/graph-api"
export DOMAIN="graph-api.phung.cc"
export VERSION=$(git rev-parse --short HEAD)

kubectl kustomize deployment/template | envsubst > deployment/prod.yml

# apply only if not on github actions runner
if [ ! -n "$GITHUB_ACTIONS" ]; then
    kubectl apply -f deployment/prod.yml
fi