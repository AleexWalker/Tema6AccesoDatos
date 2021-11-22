package exercicis

import com.db4o.Db4oEmbedded

fun main(){
    val baseDatos = Db4oEmbedded.openFile("Rutes.db4o")
    val rutes = Ruta()
    val llistaRutes = baseDatos.queryByExample<Ruta>(rutes)

    /*for (e in  llistaRutes) {
            println(e.nom + ": " + e.llistaDePunts.size + " puntos")
    }*/

    llistaRutes.forEach() {
        println( it.nom + ": " + it.llistaDePunts.size + " punts")
    }

    //Solo tenemos 2 puntos debido a que el unico ejercicio que nos falta a esta altura es el 4.4 que es en el que se inserta la nueva Ruta con sus respectivos puntos.

    baseDatos.close()
}