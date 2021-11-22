package exercicis

import com.db4o.Db4oEmbedded

fun main() {
    val baseDades = Db4oEmbedded.openFile("Rutes.db4o")
    val auxiliar = GestionarRutes2("Rutes.sqlite")
    val llistaRutes = auxiliar.llistat()

    for (i in llistaRutes) {
        //ruta = Ruta(llistaRutes[i].nom, llistaRutes[i].desnivell, llistaRutes[i].desnivellAcumulat, llistaRutes[i].llistaDePunts)
        i.mostrarRuta()
        baseDades.store(i)
    }

    //Solo tenemos 2 puntos debido a que el unico ejercicio que nos falta a esta altura es el 4.4 que es en el que se inserta la nueva Ruta con sus respectivos puntos.

    baseDades.close()
}