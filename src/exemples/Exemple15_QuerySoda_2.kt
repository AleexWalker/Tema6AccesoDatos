package exemples

import com.db4o.Db4oEmbedded
import com.db4o.ObjectContainer
import com.db4o.ObjectSet
import com.db4o.query.Query
import classesEmpleat.Empleat

fun main() {
    val bd = Db4oEmbedded.openFile("Empleats.db4o")
    val q = bd.query()          //node arrel.

    var node = q.descend("sou") //arribem a l'altura del sou, que és on posem la restricció
    node.constrain(1000).greater().and(node.constrain(1500).smaller().equal())
    node.orderDescending()

    val llista = q.execute<Empleat>()
    for (e in llista) {
        println("Nif: " + e.nif + ". Nom: " + e.nom + " (" + e.sou + ")")
    }
    bd.close()
}

