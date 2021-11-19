package exemples

import com.db4o.Db4oEmbedded
import classesEmpleat.Empleat

fun main() {

    val bd = Db4oEmbedded.openFile ("Empleats.db4o")
    val patro = Empleat("22222222b")

    // Si posàrem ací db.delete(patro) no tindría efecte, perquè e no es
    // correspon amb cap instància de la BD

    val llista = bd.queryByExample<Empleat>(patro)
    if (llista.hasNext()) {
        val e = llista.next()
        bd.delete(e)
    }
    bd.close()
}


