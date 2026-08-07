### **📝 Enunciado: Sistema de Inspección de Tráfico de Red (SITR).**

Un centro de datos requiere un nuevo motor de filtrado e inspección para su infraestructura perimetral. El sistema debe evaluar paquetes de datos entrantes, aplicar un proceso de auditoría estandarizado y emitir alertas tempranas frente a tráfico anómalo.

#### **⚙️ Datos necesarios y obligatorios a completar.**

* **APELLIDO, NOMBRE**:  Oshiro, Natalia  
* **COMISIÓN**:  03
* **DNI**: 35945782

#### **📝 Consideraciones Iniciales y Criterio de Evaluación.**

Se evaluará cada solución prestando especial atención a:

* **Pautas obligatorias** (descritas abajo) correctamente cumplidas.  
* **Entendimiento y correcta aplicación de los conceptos vistos**: Patrones de diseño (**Composite**, **Template Method**, **Observer**), manejo de excepciones, test unitarios (**GWT** y **AAA**).  
* **Prolijidad y legibilidad** del código presentado.  
* El código entregado debe tener los tests suficientes que garanticen el correcto funcionamiento (*esperado 75%+*).  
* La solución debe aplicar patrones de diseño apropiados. **El uso inadecuado de patrones descalifica el examen automáticamente**.  
* Las entregas que tengan un solo commit o no reflejen el progreso serán desaprobadas. **Realizar commits/push periódicamente**.

#### **📌 Pautas obligatorias para la entrega.**

Utilizaremos un sistema de 3 'checkpoints', a saber:

* ⚠️ El código entregado debe compilar obligatoriamente. **Un parcial entregado cuyo código no compila queda desaprobado automáticamente**.  
* **Checkpoint 1**: Push inicial completando los datos personales.  
* **Checkpoint 2**: Push antes de realizar el primer test.  
* **Checkpoint 3**: Push al final de la entrega, al terminar sus test.

# **🚨 Motor de Seguridad Perimetral 🌐.**

#### **👨‍🏫 Primera Parte \- Reglas de Filtrado (Composición de Políticas).**

**📋 Requerimientos.**  
Para decidir si un PaqueteRed (que conoce su IP de origen, IP de destino y puerto) es sospechoso, el sistema utiliza **Políticas de Filtrado**. Una política evalúa un paquete y determina si debe ser *rechazado*.  
Existen políticas simples:

* 🛑 **Bloqueo por IP**: Rechaza el paquete si proviene de una IP maliciosa específica.  
* 🚪 **Bloqueo por Puerto**: Rechaza el paquete si se dirige a un puerto restringido.

Sin embargo, los administradores de infraestructura necesitan crear reglas dinámicas complejas agrupando políticas. El sistema debe soportar:

* 🛡️ **Política Estricta (AND)**: Agrupa múltiples políticas. Rechaza el paquete si **al menos una** de las políticas internas lo rechaza.  
* ⚖️ **Política Permisiva (OR)**: Agrupa múltiples políticas. Rechaza el paquete solo si **todas** las políticas internas lo rechazan.

*Debe ser posible anidar agrupaciones de políticas dentro de otras agrupaciones sin límite de profundidad funcional.*

#### **👨‍🏫 Segunda Parte \- Flujo de Inspección Estandarizado.**

Existen distintos tipos de motores de inspección en la red (por ejemplo, MotorFrontera para tráfico de internet y MotorInterno para la red LAN). Sin embargo, por normativas de auditoría, **todos los motores deben ejecutar exactamente la misma secuencia inalterable** para procesar un paquete:

> 1. **Registro de Ingreso**: Se asienta en el historial del motor la IP de origen del paquete.  
> 2. **Filtrado Básico**: Se evalúa el paquete contra una *Política de Filtrado* (desarrollada en la Primera Parte). Si la política rechaza el paquete, se interrumpe el flujo inmediatamente y se marca el paquete como "Amenaza".  
> 3. **Inspección Profunda (Específica)**: Si superó el filtrado básico, se somete a un análisis de carga útil. **Este paso varía según el tipo de motor**:  
   * El MotorFrontera simula un escaneo de firmas de malware.  
   * El MotorInterno valida que la IP posea un token de confianza de la intranet.  
> 4. **Aprobación**: Si el paquete supera todas las fases anteriores, se lo marca definitivamente como "Tráfico Seguro".

*El diseño debe garantizar que ningún desarrollador junior pueda alterar el orden de estos 4 pasos al crear un nuevo tipo de motor.*

#### **👨‍🏫 Tercera Parte \- Telemetría y Alertas del SIEM.**

El departamento de ingeniería requiere que los sistemas periféricos de ciberdefensa reaccionen en tiempo real cada vez que, durante el *Flujo de Inspección*, un paquete es catalogado como **"Amenaza"** (interrumpido en el paso 2).  
El motor debe permitir:

* Registrar componentes de monitoreo de forma dinámica.  
* Eliminar sistemas interesados.  
* Avisar a todos los inscriptos de manera automática en el instante en que un paquete es rechazado, enviando el PaqueteRed como evidencia.

Sistemas interesados a implementar para esta entrega:

> 1. 📝 **Consola de Auditoría**: Registra en un log unificado un mensaje de texto indicando: *"Alerta de seguridad: Paquete rechazado desde la IP \[IP\]"*.  
> 2. 🧱 **Auto-Mitigador**: Simula la acción de agregar automáticamente la IP infractora a una lista negra temporal en el firewall.

#### **⚠️ Manejo de Excepciones y Reglas de Negocio.**

El sistema debe garantizar la consistencia de los datos modelando correctamente las siguientes situaciones anómalas mediante el uso de excepciones en Java:

> 1. **Excepción No Verificada (Unchecked Exception) \- Paquete Malformado:** Si se intenta instanciar un PaqueteRed con un puerto fuera de rango (menor a 1 o mayor a 65535), el sistema debe lanzar un error. Dado que es un error de programación instanciar puertos inválidos, el compilador no debe obligar a capturar esta falla explícitamente.  
> 2. **Excepción Verificada (Checked Exception) \- Violación de Segmentación:** Se asume que la subred "10.0.0.x" es de uso exclusivo del centro de ingeniería. Si un paquete intenta ingresar con destino a cualquier IP que comience con "10.0.0." y el puerto es el 22 (SSH), el motor debe lanzar un error estricto de regla de negocio al intentar iniciar el Flujo de Inspección. Al ser un intento de intrusión esperable pero crítico, el diseño debe obligar en tiempo de compilación a que el invocador capture y maneje la situación.

#### **⚙️ Restricciones de Diseño.**

* Evitar soluciones basadas en condicionales (if / switch / instanceof) para evaluar los distintos tipos de reglas de filtrado o para notificar a los observadores.  
* El Flujo de Inspección debe delegar el comportamiento específico (Inspección Profunda) a sus subclases sin permitir que estas sobreescriban la receta general del algoritmo.  
* El diseño debe permitir agregar en el futuro nuevos sistemas de monitoreo o nuevos tipos de políticas de filtrado sin modificar las clases base existentes (respetando el Principio Abierto/Cerrado).

#### **⏱️ Condiciones de Evaluación.**

* **Tiempo máximo de resolución:** 120 minutos reloj.  
* **Metodología:** Desarrollo Guiado por Pruebas (TDD) y refactors sucesivos aplicando la estructura **AAA** en los escenarios.  
* **Métrica de éxito:** Se exige alcanzar una cobertura de código (Code Coverage) de al menos un **75%**.