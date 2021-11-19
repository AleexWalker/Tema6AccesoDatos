package exemples

import com.db4o.Db4oEmbedded
import classesEmpleat.Empleat

fun main() {
    val conf = Db4oEmbedded.newConfiguration()
    conf.common().objectClass("classesEmpleat.Empleat").cascadeOnDelete(true)

    val bd = Db4oEmbedded.openFile(conf,"Empleats.db4o")
    val patro = Empleat("33333333c")

    // Si posàrem ací db.delete(patro) no tindría efecte, perquè e no es
    // correspon amb cap instància de la BD

    val llista = bd.queryByExample<Empleat>(patro)
    if (llista.hasNext()) {
        val e = llista.next()
        bd.delete(e)
    }
    bd.close()
}


