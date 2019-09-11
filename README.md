# Blueprint Management 2 Laboratory

### Integrantes:

- Willson Melo Merchan
- Jeymar Vega


### Part II

Agregamos lo necesario a los métodos get del controlador, para hacer get a los BluePrint.
![alt text1](https://github.com/swilsonmelo/Lab4Arsw/blob/master/img/Get.PNG)

### Part II

Agregamos lo necesario a los métodos Post del controlador, en este caso es para agregar un nuevo BluePrint.
![alt text1](https://github.com/swilsonmelo/Lab4Arsw/blob/master/img/Post.PNG)

Agregamos lo necesario a los métodos Put del controlador, para poder modificar algún BluePrint.
![alt text1](https://github.com/swilsonmelo/Lab4Arsw/blob/master/img/Put.PNG)



### Part III

- Hicimos lo necesario para que funcionará correctamente de manera concurrente. Sincronizando el hashMap que contiene los BluePrint.
![alt text1](https://github.com/swilsonmelo/Lab4Arsw/blob/master/img/synchronizedMap.PNG)
![alt text1](https://github.com/swilsonmelo/Lab4Arsw/blob/master/img/Synchronized.PNG)

 -  Qué condiciones de carrera podrían ocurrir?
 
 -  Que el usuario haga un get y un put al mismo tiempo, pues puede que tengan visión de unos bluePrints que hallan sido modificados.
 -  Trata de modificar el mismo BluePrint al mismo tiempo
 -  Que el usuario haga un get y un post al mismo tiempo.
 
	 
 -  Cuáles son las regiones críticas respectivas?
 -  La única región critica encontrada es un hashMap que contiene todos los bluePrints.
