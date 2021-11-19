package exemples

import com.db4o.Db4oEmbedded
import classesEmpleat.Empleat

fun main() {
    var bd = Db4oEmbedded.openFile ("Empleats.db4o")

    val patro =  Empleat("11111111a")
    val llista = bd.queryByExample<Empleat>(patro)
    if (llista.hasNext()) {
        var e = llista.next()
        if (e.sou != null) {
            e.sou = e.sou.toString().toDouble() + 200.0
        }

        bd.close() // Tanquem i tornem a obrir la BD, per veure que hem
        // perdut la correspondència de e amb l'objecte de la BD

        bd = Db4oEmbedded.openFile("Empleats.db4o")

        bd.store(e)
    }
    bd.close()
}


