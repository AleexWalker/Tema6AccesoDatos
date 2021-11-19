package exemples

import classesEmpleat.Adreca
import classesEmpleat.Empleat
import classesEmpleat.Telefon
import com.db4o.Db4oEmbedded

fun main() {
    val bd = Db4oEmbedded.openFile ("Empleats.db4o")
    val e = Empleat("22222222b", "Berta", 10, 35, 1700.0, null, null, null, null, null)
    val f = Empleat("33333333c", "Clàudia", 20, 37, 1500.0, null, null, null, null, null)

    //les dades més complicades les introduïm de forma especial
    e.adreca = Adreca ("C/ Enmig, 7", "12001", "Castelló")
    val corr = arrayOf( "alu22222222b@ieselcaminas.org", "berta@gmail.com" )
    e.correus_e = corr
    val tels = arrayOf(Telefon(true,"666555444"), Telefon(false,"964223344"))
    e.telefons = tels

    f.adreca = Adreca ("C/ de Dalt, 7", null, "Borriana")
    val corr2 = arrayOf("alu33333333c@ieselcaminas.org")
    f.correus_e = corr2

    bd.store(e)
    bd.store(f)

    bd.close()
}

