package exemples

import com.db4o.Db4oEmbedded
import com.db4o.ObjectContainer
import com.db4o.ObjectSet
import com.db4o.query.Predicate

import classesEmpleat.Empleat

fun main() {
    val bd = Db4oEmbedded.openFile("Empleats.db4o")
    val max = 1500.0
    val min = 1000.0
    val llista = bd.query(object: Predicate<Empleat>() {
        override
        fun match(emp: Empleat): Boolean {
            if (emp.sou.toString().toDouble() <= max && emp.sou.toString().toDouble() >= min)
                return true
            else
                return false
        }
    })

    for (e in llista) {
        System.out.println(e.nom + " (" + e.sou + ")")
    }
    bd.close()
}

