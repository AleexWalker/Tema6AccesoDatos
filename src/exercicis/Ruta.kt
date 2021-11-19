package exercicis

import java.io.EOFException
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.io.Serializable

class Ruta (var nom: String?, var desnivell: Int?, var desnivellAcumulat: Int?, var llistaDePunts: MutableList<PuntGeo> = mutableListOf<PuntGeo>()): Serializable {
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
        val ficheroLecturaObjeto = ObjectInputStream(FileInputStream("Rutes.obj"))
        try {
            while (true){
                val lectura = ficheroLecturaObjeto.readObject() as Ruta
                println("Nombre: " + lectura.nom)
                println("Desnivel: " + lectura.desnivell)
                println("Desnivel Acumulado: " + lectura.desnivellAcumulat)
                val puntos = lectura.llistaDePunts
                for (i in 1..puntos.lastIndex){
                    println("\tNombre Punto: " + lectura.getPuntNom(i) + " (" + lectura.getPuntLatitud(i) + ") "+ " (" + lectura.getPuntLongitud(i) + ")")
                }
                println("\n")
            }
        } catch (eof : EOFException){
            ficheroLecturaObjeto.close()
        }
        // Aquest és el mètode que heu d'implementar vosaltres
    }
}