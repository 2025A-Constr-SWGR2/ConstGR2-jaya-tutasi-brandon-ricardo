const paqueteSuma = require('2025a-swgr2-brjt-suma');
const paqueteMultiplicacion = require('2025a-swgr2-brjt-multiplicacion');
const paqueteResta = require('2025a-swgr2-brjt-resta');
const paqueteDivision = require('2025a-swgr2-brjt-division');

const respuestaSuma = paqueteSuma.suma(2, 1);
const respuestaMultiplicacion = paqueteMultiplicacion.multiplicacion(2, 5);
const respuestaResta = paqueteResta.resta(3, 2);
const respuestaDivision = paqueteDivision.division(9, 3);

console.log('La respuesta de la suma es: ', respuestaSuma);
console.log('La respuesta de la resta es: ', respuestaResta);
console.log('La respuesta de la multiplicacion es: ', respuestaMultiplicacion);
console.log('La respuesta de la division es: ', respuestaDivision);