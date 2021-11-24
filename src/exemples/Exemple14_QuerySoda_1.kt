package exemples

import com.db4o.Db4oEmbedded
import com.db4o.ObjectContainer
import com.db4o.ObjectSet
import com.db4o.query.Query
import classesEmpleat.Empleat

fun main() {
    val bd = Db4oEmbedded.openFile("Empleats.db4o")
    val q = bd.query ()          //node arrel.

    var node = q.descend ("nif") //arribem a l'altura del nif, que és on posem la restricció
    node.constrain("11111111a")

    val llista = q.execute<Empleat>()
    for (e in llista){
        println("Nif: " + e.nif + ". Nom: " + e.nom + " (" + e.sou + ")")
    }
    bd.close()
}

