# Projectum
Projectum es una aplicación web que nos permite gestionar la cartera de proyectos TI de la Universidad de Cádiz.
Con esta aplicación, podrás evaluar, priorizar y hacer un seguimiento de los proyectos de la universidad.
También, permite valorar el alineamiento estratégico, la viabilidad técnica y la disponibilidad de recursos
para tomar decisiones informadas, utilizando criterior parametrizables.  Además, facilita la gestión del grado de avance de los proyectos, impulsando la 
planificación estratégica y la optimización de recursos en el entorno académico.

## Integrantes

- Pablo García Bravo
- Laura Guerrero Ramos
- Antonio Richarte González

## Descripción de proyecto
Projectum es una app realizada en `Java 23`, usando `Spring Framework` como backend y `Vaadin` como frontend. Haremos un deploy en `Amazon Web Service`, por lo que estará lista 
para uso nada mas hagamos el deploy.

## Estructura del proyecto
- [Application.java](src/main/java/projectum/Application.java): Iniciaizador de la aplicación web.
- [Security](src/main/java/projectum/security): Carpeta con toda la configuración de la seguridad.
- [Vistas](src/main/java/projectum/vistas): Carpeta junto con todas las vistas de la aplicación.
- [MainLayout](src/main/java/projectum/vistas/MainLayout) en la carpeta `projectum/vistas`: Estructura de cómo será la vista la aplicación por el usuario.
- [Data](src/main/java/projectum/data): Carpeta que contiene `/entidades`, `/repositorios` y `/servicios` para la gestión de los datos de nuestra aplicación.


## Referencias Útiles

- Página principal de Vaadin: [vaadin.com](https://vaadin.com)
- Documentación de Vaadin: [vaadin.com/docs/latest](https://vaadin.com/docs/latest/)
- Tutorial de Vaadin: [vaadin.com/docs/latest/getting-started/tutorial](https://vaadin.com/docs/latest/getting-started/tutorial)   
- Vaadin Initializer: [start.vaadin.com](https://start.vaadin.com/app)
- Página principal de Spring Framework: [spring.io](https://spring.io/projects/spring-framework)
- Documentación de Spring Framework: [docs.spring.io](https://docs.spring.io/spring-framework/reference/index.html)
- Spring Initializer: [start.spring.io](https://start.spring.io)
- Amazon Web Service: [aws.amazon.com](https://aws.amazon.com/es/free/?trk=2d5aad89-991b-4184-98b5-1f562e3102c8&sc_channel=ps&ef_id=Cj0KCQiAst67BhCEARIsAKKdWOnaExyXBDUMCLour7zRM41bCeY7sPtTeP534znQvSwt0QCmsRY4PkoaAr3MEALw_wcB:G:s&s_kwcid=AL!4422!3!561218200767!e!!g!!amazon%20web%20services!15250970096!135343037888&gclid=Cj0KCQiAst67BhCEARIsAKKdWOnaExyXBDUMCLour7zRM41bCeY7sPtTeP534znQvSwt0QCmsRY4PkoaAr3MEALw_wcB&all-free-tier.sort-by=item.additionalFields.SortRank&all-free-tier.sort-order=asc&awsf.Free%20Tier%20Types=*all&awsf.Free%20Tier%20Categories=*all)

