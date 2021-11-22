package exercicis

import java.io.EOFException
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.io.Serializable

class Ruta (var nom: String?, var desnivell: Int?, var desnivellAcumulat: Int?, var llistaDePunts: MutableList<PuntGeo> = mutableListOf<PuntGeo>()): Serializable {

    constructor() : this(null, null, null, arrayListOf<PuntGeo>())
    companion object {
        private const val serialVersionUID: Long = 1
    }

    fun addPunt(p: PuntGeo){
        llistaDePunts.add(p)
    }

    fun getPunt(i: Int): PuntGeo{
        return llistaDePunts.get(i)
    }

    fun getPuntNom(i: Int): String {
        return llistaDePunts.get(i).nom
    }

    fun getPuntLatitud(i: Int): Double {
        return llistaDePunts.get(i).coord.latitud
    }

    fun getPuntLongitud(i: Int): Double {
        return llistaDePunts.get(i).coord.longitud
    }

    fun size(): Int {
        return llistaDePunts.size
    }

    fun mostrarRuta() {
        println("Nombre: " + this.nom)
        println("Desnivel: " + this.desnivell)
        println("Desnivel Acumulado: " + this.desnivellAcumulat)
        val puntos = this.llistaDePunts
        for (i in 0..puntos.lastIndex){
            println("" + (i+1) + "\tNombre Punto: " + this.getPuntNom(i) + " (" + this.getPuntLatitud(i) + ") "+ " (" + this.getPuntLongitud(i) + ")")
        }
        println("\n")
        // Aquest és el mètode que heu d'implementar vosaltres
    }
}