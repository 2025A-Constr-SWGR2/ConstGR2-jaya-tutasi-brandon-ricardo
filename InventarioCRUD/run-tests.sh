#!/bin/bash
# Script para ejecutar pruebas unitarias con cobertura

echo "🔧 Configurando entorno de pruebas..."
cd "$(dirname "$0")" || exit 1

echo "🧹 Limpiando builds anteriores..."
mvn clean

echo "🧪 Ejecutando pruebas unitarias..."
mvn test

TEST_RESULT=$?

echo "📊 Generando reporte de cobertura..."
mvn jacoco:report

if [ $TEST_RESULT -eq 0 ]; then
  echo "✅ Todas las pruebas pasaron correctamente"
else
  echo "❌ Algunas pruebas fallaron"
fi

exit $TEST_RESULT