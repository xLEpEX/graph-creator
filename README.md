# Graph-API für QVANTUM

Dies ist ein Projekt, welches im laufe eines Bewerbungsprozesse für Qvantum-plan umgesetzt wurde.
<https://graph-api.phung.cc>

# Aufgabe

Goal of this task is the design and the implementation of a simple graph API. This API allows to create
a directed graph and to performtopological sorting on its nodes.

### Acceptance Criteria

- The API allows management of exactly one graph.
- The API allows to add nodes to the graph.
- The API allows to add edges to the graph.
- The API allows to retrieve the full graph.
- The API allows to retrieve nodes in topological order.

# Deployment

Die API wird in ein Docker Image gebaut und auf [Docker.io](https://hub.docker.com/repository/docker/xlepex/graph-api/general) hochgeladen mit [kustomize](https://kustomize.io) werden alle benötigten Konfigurations dateien erstellt und mit kubectl in ein privates Cluster deployed. Dies alles wird mit [github actions](https://github.com/features/actions) umgesetzt

# Docs

Eine dokumentation zu den einzelnen Endpoints wird per swagger ui zu verfügung gestellt und ist erreichbar unter
<https://graph-api.phung.cc>
