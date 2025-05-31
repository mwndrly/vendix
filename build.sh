#!/bin/bash

# Cria pasta de saída, se não existir
mkdir -p out

# Compila todos os .java
javac -d out src/models/*.java src/utils/*.java

# Executa o programa
java -cp out models.Main
