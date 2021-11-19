package exemples

import com.db4o.Db4oEmbedded
import classesEmpleat.Adreca
import classesEmpleat.Empleat

fun main() {
    val bd = Db4oEmbedded. openFile("Empleats.db4o")

    val patro =  Empleat(null)
    patro.departament = 10
    patro.adreca = Adreca (null, null, "Castelló")

    val llista = bd.queryByExample<Empleat>(patro)
    for (e in llista) {
        System.out.println("Nif: " + e.nif + ". Nom: " + e.nom
                + ". Departament: " + e.departament	+ ". Població: " + e.adreca?.poblacio
        )
    }
    bd.close()
}


