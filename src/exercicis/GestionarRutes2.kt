package exercicis

import exercicis.Coordenades
import exercicis.PuntGeo
import exercicis.Ruta
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

class GestionarRutes2(var url: String) {
    var conexion: Connection
    var st: Statement

    init {

        conexion = DriverManager.getConnection("jdbc:sqlite:" + url)
        st = conexion.createStatement()
        val sentenciaSQLRUTES = "CREATE TABLE IF NOT EXISTS RUTES(" +
                "num_r INTEGER PRIMARY KEY, " +
                "nom_r TEXT, " +
                "desn INTEGER, " +
                "desn_ac INTEGER " +
                ")"
        val sentenciaSQLPUNTS = "CREATE TABLE IF NOT EXISTS PUNTS(" +
                "num_r INTEGER, " +
                "num_p INTEGER, " +
                "nom_p TEXT, " +
                "latitud REAL, " +
                "longitud REAL, " +
                "FOREIGN KEY(num_r) REFERENCES RUTES(num_r), " +
                "PRIMARY KEY (num_r,num_p) " +
                ")"
        st.executeUpdate(sentenciaSQLRUTES)
        st.executeUpdate(sentenciaSQLPUNTS)
        st.close()
    }

    fun llistat(): ArrayList<Ruta> {
        val arrayRutas = arrayListOf<Ruta>()
        st = conexion.createStatement()
        val sentenciaSQLRutas = "SELECT num_r, nom_r, desn, desn_ac FROM RUTES"
        val ruta = st.executeQuery(sentenciaSQLRutas)
        while (ruta.next()) {
            val sentenciaSQLPunts = "SELECT nom_p, latitud, longitud FROM PUNTS WHERE num_r = ${ruta.getInt(1)}"
            st = conexion.createStatement()
            val punts = st.executeQuery(sentenciaSQLPunts)
            val arrayPunts = arrayListOf<PuntGeo>()
            while (punts.next()) {
                arrayPunts.add(PuntGeo(punts.getString(1), Coordenades(punts.getDouble(2), punts.getDouble(3))))
            }
            arrayRutas.add(Ruta(ruta.getString(2), ruta.getInt(3), ruta.getInt(4), arrayPunts))
        }
        st.close()
        return arrayRutas
    }
}