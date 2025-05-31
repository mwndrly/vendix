#!/bin/bash

# Cria pastas de saída, se não existirem
mkdir -p out
mkdir -p bin

# Compila só o código principal (sem testes)
javac -d out src/models/*.java src/utils/*.java

# Se não for rodar teste, executa o programa normalmente
if [ "$1" != "test" ]; then
  java -cp out models.Main
  exit 0
fi

# Função para compilar e rodar os testes com JUnit no classpath
run_tests() {
  echo "Compilando testes com JUnit..."
  # Compila código fonte e testes, incluindo libs no classpath
  javac -cp "libs/*:src" -d bin src/models/*.java src/utils/*.java src/test/*.java

  echo "Executando testes com JUnit..."
  # Roda o JUnit Platform Console
  java -jar libs/junit-platform-console-standalone-1.10.0.jar --class-path bin --scan-class-path
}

# Se chamar ./build.sh test, roda testes
if [ "$1" == "test" ]; then
  run_tests
fi
