package exemples

import com.db4o.Db4oEmbedded
import com.db4o.ObjectContainer
import com.db4o.ObjectSet
import com.db4o.query.Query
import classesEmpleat.Empleat

fun main() {
    val bd = Db4oEmbedded.openFile("Empleats.db4o")
    val q = bd.query()          //node arrel.

    var node = q.descend("departament") //arribem a l'altura del departament, que és on posem la restricció
    node.constrain(10)

    node = q.descend("adreca").descend("poblacio") //i ara arribem a l'altura de la població de l'adreça
    node.constrain("Castelló")


    val llista = q.execute<Empleat>()
    for (e in llista) {
        println("Nom: " + e.nom + ". Població: " + e.adreca?.poblacio + ". Departament: " + e.departament)
    }
    bd.close()
}

