package exemples

import com.db4o.Db4oEmbedded
import classesEmpleat.Empleat

fun main() {
    val conf = Db4oEmbedded.newConfiguration()
    conf.common().objectClass("classesEmpleat.Empleat").cascadeOnUpdate(true)

    val bd = Db4oEmbedded.openFile(conf, "Empleats.db4o")

    val patro = Empleat("11111111a")
    val llista = bd.queryByExample<Empleat>(patro)
    if (llista.hasNext()) {
        var e = llista.next()
        if (e.sou != null) {
            e.sou = e.sou.toString().toDouble() + 200.0
        }
        val adr = e.adreca
        adr?.carrer = "Pl. Rei en Jaume, 15"
        adr?.codipostal = "12002"
        e.adreca = adr
        bd.store(e)
    }
    bd.close()
}

